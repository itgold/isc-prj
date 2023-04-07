package com.iscweb.service.composite.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.service.ICompositeCache;
import com.iscweb.service.composite.leaf.RegionComposite;
import com.iscweb.service.util.meta.DoorDeviceMeta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class CompositeCache implements ICompositeCache {

    @Getter
    @Value("${isc.services.composite.enableCaching:#{true}}")
    protected boolean enableCaching;

    /**
     * This is cached root node for the composite tree.
     */
    @Getter(AccessLevel.PRIVATE)
    private final AtomicReference<IRegionComposite> rootCompositeCache = new AtomicReference<>(null);

    /**
     * This is cache Map of composite elements aggregated by ID for faster node lookup.
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<String, IRegionComposite> nodeCache = Maps.newHashMap();

    /**
     * This is special lock is to be used for concurrent modifications of the cache.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Getter(AccessLevel.PRIVATE)
    private final Lock readLock = readWriteLock.readLock();

    @Getter(AccessLevel.PRIVATE)
    private final Lock writeLock = readWriteLock.writeLock();

    @Override
    public IRegionComposite getRootNode() {
        try {
            getReadLock().lock();

            return getRootCompositeCache().get();
        } finally {
            getReadLock().unlock();
        }
    }

    @Override
    public IRegionComposite getNode(String nodeId) {
        try {
            getReadLock().lock();

            return getNodeCache().get(nodeId);
        } finally {
            getReadLock().unlock();
        }
    }

    @Override
    public void resetCache(IRegionComposite root) {
        Map<String, IRegionComposite> newCache = Maps.newHashMap();
        CompositeHelperService.traverseTree(root, node -> newCache.put(node.getEntityDto().getId(), node));

        try {
            getWriteLock().lock();

            getRootCompositeCache().set(root);
            getNodeCache().clear();
            getNodeCache().putAll(newCache);
        } finally {
            getWriteLock().unlock();
        }
    }

    /**
     * Generate list of full paths from root down to the deepest child.
     *
     * @param children List of sub-nodes to check
     * @param parents  Direct parent nodes up to the root
     * @return List of all chains from root node to each leaf
     */
    private List<List<RegionComposite>> generateFullPaths(Collection<IRegionComposite> children, List<RegionComposite> parents) {
        List<List<RegionComposite>> parentChains = Lists.newArrayList();

        children.forEach(child -> {
            if (child.getCompositeType() == RegionCompositeType.CONTAINER /* && ((RegionDto) child.getEntityDto()).getStatus() == RegionStatus.ACTIVATED */) {
                List<RegionComposite> childPath = Lists.newArrayList(parents);
                childPath.add((RegionComposite) child);

                Map<String, IRegionComposite> grandChildren = ((RegionComposite) child).getChildren();
                if (grandChildren != null && grandChildren.size() > 0) {
                    List<List<RegionComposite>> chains = generateFullPaths(grandChildren.values(), childPath);
                    if (chains.size() > 0) {
                        parentChains.addAll(chains);
                    } else {
                        parentChains.add(childPath);
                    }
                } else {
                    parentChains.add(childPath);
                }
            }
        });

        return parentChains;
    }

    /**
     * Find matching sub-trees with provided parent path by names
     * Note: the search is done from down to up. I.e. the deepest children first
     *
     * @param path Path to match with
     * @return List of node guids which are matching with the path
     */
    @Override
    public Set<String> findChildRegionsByParentPath(String path) {
        Set<String> result = Sets.newHashSet();

        if (isEnableCaching() && path != null) {
            String parentPath = path.trim();

            // Parse search path
            List<String> parentNames = Stream.of(parentPath.split("[->]"))
                                             .map(String::trim)
                                             .map(String::toLowerCase)
                                             .filter(name -> name.length() > 0)
                                             .collect(Collectors.toList());
            if (parentNames.size() > 0) {
                Collections.reverse(parentNames);
                Map<String, IRegionComposite> children = ((RegionComposite) getRootNode()).getChildren();
                List<List<RegionComposite>> parentPaths = children != null && children.size() > 0 ? generateFullPaths(children.values(), Lists.newArrayList()) : Lists.newArrayList();
                parentPaths.forEach(Collections::reverse);

                parentPaths.forEach(chain -> {
                    List<RegionComposite> parentChain = Lists.newArrayList(chain);
                    // String debugPath =  parentChain.stream().map(c -> c.getEntityDto().getName()).collect(Collectors.joining(" > "));
                    // if (debugPath.indexOf("> 3 > B2 >") >= 0) {
                    //     log.warn("Found!!!");
                    // }

                    int isMatching = -1;
                    while (parentChain.size() >= parentNames.size()) {
                        int nameIdx = 0;
                        for (int j = 0; j < parentChain.size(); j += 1) {
                            String name = parentChain.get(j).getEntityDto().getName();
                            if (isNameMatching(name, parentNames.get(nameIdx))) {
                                nameIdx += 1;
                                if (nameIdx == parentNames.size()) {
                                    isMatching = j;
                                    break;
                                }
                            } else if (nameIdx > 0) {
                                break;
                            }
                        }

                        if (isMatching < 0 && parentChain.size() > 1) {
                            parentChain.remove(0);
                        } else {
                            int index = isMatching - parentNames.size() + 1;
                            if (index >= 0 && index < parentChain.size()) {
                                String firstMatchingId = parentChain.get(index).getEntityDto().getId();
                                for (RegionComposite regionComposite : chain) {
                                    String id = regionComposite.getEntityDto().getId();
                                    result.add(id);
                                    if (id.equals(firstMatchingId)) {
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }

                    // log.debug("Path {} / {} / {}", isMatching, debugPath);
                });
            }
        }

        return result;
    }

    /**
     * Find list of composite nodes by ID
     * <p>
     * The algorithm is using level by level composite tree traversal.
     *
     * @param nodeIds List of node IDs to look for
     * @return List of all found composite nodes
     */
    @Override
    public List<IRegionComposite> findNodes(Collection<String> nodeIds) {
        final Map<String, IRegionComposite> result = Maps.newHashMap();

        try {
            getReadLock().lock();

            nodeIds.forEach(nodeId -> {
                IRegionComposite compositeNode = getNodeCache().get(nodeId);
                if (compositeNode != null) {
                    result.put(nodeId, compositeNode);
                }
            });
        } finally {
            getReadLock().unlock();
        }

        return Lists.newArrayList(result.values());
    }

    @Override
    public void refreshCreated(IRegionComposite compositeNode) {
        BaseSchoolEntityDto node = compositeNode.getEntityDto();
        List<IRegionComposite> parents = findNodes(node.getParentIds());
        try {
            getWriteLock().lock();

            parents.forEach(parent -> {
                Map<String, IRegionComposite> children = ((RegionComposite) parent).getChildren();
                children.put(node.getId(), compositeNode);
                Map<String, IRegionComposite> sortedChildren = children.values().stream()
                                                                       .collect(Collectors.toMap(p -> p.getEntityDto().getId(),
                                                                                                 composite -> composite)).entrySet()
                                                                       .stream()
                                                                       .sorted(Map.Entry.comparingByValue())
                                                                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                                                                 (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                ((RegionComposite) parent).setChildren(sortedChildren);
            });
            getNodeCache().put(node.getId(), compositeNode);
        } finally {
            getWriteLock().unlock();
        }
    }

    @Override
    public void refreshUpdated(IRegionComposite compositeNode) {
        BaseSchoolEntityDto node = compositeNode.getEntityDto();
        Set<String> processed = Sets.newHashSet();
        List<IRegionComposite> parents = findParents(node.getId());

        try {
            getWriteLock().lock();

            // Note: node.getParentIds() can be different from existing parent nodes.
            parents.forEach(parent -> {
                RegionComposite parentNode = (RegionComposite) parent;
                if (node.getParentIds().contains(parentNode.getEntityDto().getId())) {
                    Map<String, IRegionComposite> children = parentNode.getChildren();
                    children.put(node.getId(), compositeNode);

                    Map<String, IRegionComposite> sortedChildren = children.values().stream()
                            .collect(Collectors.toMap(p -> p.getEntityDto().getId(),
                                    composite -> composite)).entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                    ((RegionComposite) parent).setChildren(sortedChildren);
                } else {
                    parentNode.getChildren().remove(node.getId());
                }
                processed.add(parentNode.getEntityDto().getId());
            });

            List<String> newParents = node.getParentIds().stream().filter(id -> !processed.contains(id)).collect(Collectors.toList());
            findNodes(newParents)
                    .forEach(parent -> ((RegionComposite) parent).getChildren().put(node.getId(), compositeNode));
            getNodeCache().put(node.getId(), compositeNode);
        } finally {
            getWriteLock().unlock();
        }
    }

    @Override
    public void refreshDeleted(String nodeId) {
        if (isEnableCaching()) {
            List<IRegionComposite> parents = findParents(nodeId);
            try {
                getWriteLock().lock();

                parents.forEach(parent -> ((RegionComposite) parent).getChildren().remove(nodeId));
                getNodeCache().remove(nodeId);
            } finally {
                getWriteLock().unlock();
            }
        }
    }

    @Override
    public IRegionComposite cloneComposite(String compositeId, CompositeFilter filter, CompositeNodeCloner cloner) {
        IRegionComposite result = null;

        List<IRegionComposite> root = findNodes(List.of(compositeId));
        if (!CollectionUtils.isEmpty(root)) {
            try {
                getReadLock().lock();
                result = clone(root.get(0), filter, cloner);
            } finally {
                getReadLock().unlock();
            }
        }

        return result;
    }

    @Override
    public Collection<IRegionComposite> findChildren(Collection<IRegionComposite> roots, CompositeFilter filter, CompositeNodeCloner cloner) {
        Set<IRegionComposite> nodes = Sets.newTreeSet(Comparator.comparing(a -> a.getEntityDto().getId()));

        for (IRegionComposite rootNode : roots) {
            if (rootNode.getCompositeType() == RegionCompositeType.CONTAINER) {
                Map<String, IRegionComposite> children = ((RegionComposite) rootNode).getChildren();
                Collection<IRegionComposite> subNodes = findChildren(children.values(), filter, cloner);
                nodes.addAll(subNodes);
            } else if (filter.enabled(RegionCompositeType.LEAF)) {
                if (filter.enabled(((IExternalEntityDto) rootNode.getEntityDto()).getEntityType())) {
                    nodes.add(cloner.clone(rootNode));
                }
            }
        }

        return nodes;
    }

    private boolean isNameMatching(String nodeName, String filter) {
        // Do we need some twisted logic to compare with the district or school region name?
        // We probably have to find out if it is one of the
        // REGIONS_MAPPING then ignore single symbol filter.
        String name = nodeName.toLowerCase();
        boolean matching = name.contains(filter);
        if (!matching) {
            String mappedName = DoorDeviceMeta.REGIONS_MAPPING.get(filter.toUpperCase());
            if (mappedName != null) {
                matching = name.contains(mappedName.toLowerCase());
            }
        }

        return matching;
    }

    /**
     * Find all parents for the provided nodeId
     * <p>
     * The algorithm is using level by level composite tree traversal.
     *
     * @param nodeId Node ID to look for
     * @return List of all parent composite nodes
     */
    private List<IRegionComposite> findParents(String nodeId) {
        Map<String, IRegionComposite> result = Maps.newHashMap();

        CompositeHelperService.traverseTree(getRootNode(), node -> {
            if (node.getCompositeType() == RegionCompositeType.CONTAINER) {
                RegionComposite parent = (RegionComposite) node;
                if (parent.getChildren().containsKey(nodeId)) {
                    result.put(parent.getEntityDto().getId(), parent);
                }
            }
        });

        return Lists.newArrayList(result.values());
    }

    /**
     * Clone composite subtree
     * <p>
     * Important: The method is not thread safe.
     *
     * @param rootNode Root node to clone
     * @param filter   Filter to apply to the subtree
     * @return Copy of the filtered composite subtree
     */
    protected IRegionComposite clone(IRegionComposite rootNode, CompositeFilter filter, CompositeNodeCloner cloner) {
        IRegionComposite result = null;
        if (rootNode.getCompositeType() == RegionCompositeType.CONTAINER) {
            result = cloner.clone(rootNode);
            Map<String, IRegionComposite> children = ((RegionComposite) result).getChildren();
            ((RegionComposite) rootNode).getChildren().forEach((key, value) -> {
                IRegionComposite child = clone(value, filter, cloner);
                if (child != null) {
                    children.put(key, child);
                }
            });
        } else if (filter.enabled(RegionCompositeType.LEAF)) {
            if (filter.enabled(((IExternalEntityDto) rootNode.getEntityDto()).getEntityType())) {
                result = cloner.clone(rootNode);
            }
        }

        return result;
    }
}
