package com.iscweb.service.composite.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.service.composite.leaf.RegionComposite;
import com.iscweb.service.entity.SchoolEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class CompositeHelperService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeCache compositeCache;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolEntityService schoolEntityService;

    public SchoolDto findSchool(String compositeGuid) {
        IRegionComposite school = null;
        IRegionComposite root = getCompositeCache().getRootNode();

        Map<String, IRegionComposite> nodeCache = Maps.newHashMap();
        IRegionComposite node = findNode(root, compositeGuid, nodeCache);
        if (node != null) {
            school = findParent(node, nodeCache,
                    parentNode -> parentNode.getCompositeType() == RegionCompositeType.CONTAINER
                            && ((RegionDto) parentNode.getEntityDto()).getType() == RegionType.SCHOOL ? Boolean.TRUE : Boolean.FALSE);
        }

        return school != null ? getSchoolEntityService().findByRegionGuid(school.getEntityDto().getId()) : null;
    }

    private IRegionComposite findNode(IRegionComposite root, String nodeId, Map<String, IRegionComposite> nodeCache) {
        Queue<IRegionComposite> stack = Lists.newLinkedList();
        stack.add(root);

        IRegionComposite theNode = null;
        while (stack.size() > 0) {
            IRegionComposite node = stack.poll();
            String id = node.getEntityDto().getId();
            if (nodeId.equals(id)) {
                theNode = node;
                break;
            } else if (node.getCompositeType() == RegionCompositeType.CONTAINER) {
                nodeCache.put(id, node);
                Map<String, IRegionComposite> children = ((RegionComposite) node).getChildren();
                stack.addAll(children.values());
            }
        }

        return theNode;
    }

    private IRegionComposite findParent(IRegionComposite node, Map<String, IRegionComposite> nodeCache, Function<IRegionComposite, Boolean> testFunction) {
        IRegionComposite result = null;

        if (testFunction.apply(node)) {
            result = node;
        } else {
            Queue<IRegionComposite> stack = Lists.newLinkedList();
            stack.add(node);

            while (stack.size() > 0 && result == null) {
                IRegionComposite parentNode = stack.poll();
                for (String parentId : parentNode.getEntityDto().getParentIds()) {
                    IRegionComposite parent = nodeCache.get(parentId);
                    if (parent != null) {
                        if (testFunction.apply(parent)) {
                            result = parent;
                        }

                        stack.add(parent);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Helper method for fast tree traversal
     *
     * Note: it is using non-recursive approach to minimize memory utilization.
     * The traversal is executed level-by-level.
     *
     * @param root Root node
     * @param visitor Visitor callback will be invoked for each tree node.
     */
    public static void traverseTree(IRegionComposite root, Consumer<IRegionComposite> visitor) {
        Queue<IRegionComposite> toProcess = Lists.newLinkedList();
        toProcess.add(root);

        while (toProcess.size() > 0) {
            IRegionComposite node = toProcess.poll();
            visitor.accept(node);

            if (node.getCompositeType() == RegionCompositeType.CONTAINER) {
                RegionComposite parent = (RegionComposite) node;
                parent.getChildren().forEach((key, value) -> toProcess.add(value));
            }
        }
    }
}
