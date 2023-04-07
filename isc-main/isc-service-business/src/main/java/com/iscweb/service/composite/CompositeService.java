package com.iscweb.service.composite;

import com.google.api.client.util.Lists;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.common.service.ICompositeCache;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.service.composite.impl.CompositeFactory;
import com.iscweb.service.composite.leaf.RegionComposite;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.RegionEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.iscweb.common.model.metadata.ConverterType.REGION;
import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * Service class for working with the region composite structures.
 *
 * @author skurenkov
 */
@Slf4j
@Service
@ThreadSafe
public class CompositeService implements IApplicationSecuredService, IApplicationComponent {

    private static final CompositeFilter DEFAULT_FILTER = CompositeFilter.build();

    @Getter
    @Value("${isc.services.composite.enableCaching:#{true}}")
    protected boolean enableCaching;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeFactory compositeFactory;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICompositeCache compositeCacheService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService entityService;

    @PostConstruct
    public void initialize() {
        invalidateCache();
    }

    /**
     * Composite builder with default filtering parameter.
     *
     * @param regionGuid region to build composite by.
     * @return region composite.
     */
    @PreAuthorize(IS_AUTHENTICATED)
    public IRegionComposite build(String regionGuid) {
        return build(regionGuid, CompositeFilter.build());
    }

    /**
     * Build method for constructing composite by a given region guid and filtering criteria.
     *
     * @param regionGuid region guid.
     * @param filter     composite filtering criteria.
     * @return a new constructed instance of region composite.
     * @see CompositeFactory#build(String, CompositeFilter)
     */
    @PreAuthorize(IS_AUTHENTICATED)
    public IRegionComposite build(String regionGuid, CompositeFilter filter) {
        IRegionComposite result;

        if (isEnableCaching()) {
            if (IRegionComposite.ROOT.equals(regionGuid) && DEFAULT_FILTER.equals(filter)) {
                //todo: Thread safety: do we need clone root to avoid concurrent modifications for child maps while traversing result?
                result = getCompositeCacheService().getRootNode();
            } else {
                result = getCompositeCacheService().cloneComposite(regionGuid, filter, node -> {
                    IRegionComposite res;
                    if (node.getCompositeType() == RegionCompositeType.CONTAINER) {
                        res = getCompositeFactory().buildRegionCompositeNode(node.getEntityDto());
                    } else {
                        res = getCompositeFactory().buildDeviceCompositeNode(node.getEntityDto());

                    }

                    return res;
                });
            }
        } else {
            result = getCompositeFactory().build(regionGuid, filter);
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public Collection<IRegionComposite> findChildren(String regionGuid, CompositeFilter filter) {
        Collection<IRegionComposite> result;

        IRegionComposite root;
        if (isEnableCaching()) {
            if (IRegionComposite.ROOT.equals(regionGuid) && DEFAULT_FILTER.equals(filter)) {
                root = getCompositeCacheService().getRootNode();
            } else {
                List<IRegionComposite> roots = getCompositeCacheService().findNodes(List.of(regionGuid));
                root = !CollectionUtils.isEmpty(roots) ? roots.get(0) : null;
            }
        } else {
            root = getCompositeFactory().build(regionGuid, filter);
        }

        result = root != null ? getCompositeCacheService().findChildren(List.of(root), filter, node -> {
            IRegionComposite res;
            if (node.getCompositeType() == RegionCompositeType.CONTAINER) {
                res = getCompositeFactory().buildRegionCompositeNode(node.getEntityDto());
            } else {
                res = getCompositeFactory().buildDeviceCompositeNode(node.getEntityDto());

            }
            return res;
        }) : null;

        return result != null ? result : Lists.newArrayList();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public Set<BaseJpaCompositeEntity> findDirectChildren(String regionGuid) {
        return findDirectChildren(regionGuid, CompositeFilter.build());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public Set<BaseJpaCompositeEntity> findDirectChildren(String regionGuid, CompositeFilter filter) {
        return getCompositeFactory().findDirectChildren(regionGuid, filter);
    }

    /**
     * Invalidate the composite cache.
     * <p>
     * Reloads the composite tree from the DB and re-initialize the node cache hash map
     */
    public void invalidateCache() {
        if (isEnableCaching()) {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
                IRegionComposite updatedTree = getCompositeFactory().build(IRegionComposite.ROOT, DEFAULT_FILTER);
                getCompositeCacheService().resetCache(updatedTree);
            }
        }
    }

    /**
     * Update cache with new node operation.
     *
     * @param node       Node been modified
     * @param updateType Update type
     * @param <D>        Base node type
     */
    public <D extends BaseSchoolEntityDto> void refreshNode(D node, CompositeCacheEntry.UpdateType updateType) {
        if (isEnableCaching()) {
            switch (updateType) {
                case CREATE:
                    refreshCreated(node);
                    break;
                case UPDATE:
                    refreshUpdated(node);
                    break;
            }
        }
    }

    /**
     * Insert new node in the cache.
     *
     * @param node New node Dto instance
     * @param <D>  Base node type
     */
    protected <D extends BaseSchoolEntityDto> void refreshCreated(D node) {
        IRegionComposite entity = createCompositeNode(node);
        getCompositeCacheService().refreshCreated(entity);
    }

    /**
     * Update existing node in the cache.
     *
     * @param node Updated node Dto instance
     * @param <D>  Base node type
     */
    protected <D extends BaseSchoolEntityDto> void refreshUpdated(D node) {
        // check if the node exists
        if (getCompositeCacheService().getNode(node.getId()) == null) {
            refreshCreated(node);
        } else {
            IRegionComposite compositeNode = createCompositeNode(node);
            getCompositeCacheService().refreshUpdated(compositeNode);
        }
    }

    /**
     * Delete existing node in the cache.
     *
     * @param nodeId Node id to delete
     */
    public void refreshDeleted(String nodeId) {
        getCompositeCacheService().refreshDeleted(nodeId);
    }

    /**
     * Creates a single composite node from provided Dto.
     *
     * @param entity Dto object
     * @param <D>    Type of Dto object
     * @return Composite node
     */
    private <D extends BaseSchoolEntityDto> IRegionComposite createCompositeNode(D entity) {
        IRegionComposite result = null;

        if (entity != null) {
            if (entity instanceof IExternalEntityDto) {
                IExternalEntityDto device = (IExternalEntityDto) entity;
                result = getCompositeFactory().buildDeviceCompositeNode(device);
            } else {
                RegionComposite regionComposite = getCompositeFactory().buildRegionCompositeNode((RegionDto) entity);
                RegionComposite existingNode = (RegionComposite) getCompositeCacheService().getNode(entity.getId());
                if (existingNode != null) {
                    regionComposite.getChildren().putAll(existingNode.getChildren());
                }
                result = regionComposite;
            }
        }

        return result;
    }

    /**
     * Matches existing region by its parent and name.
     * This method is used by the import tool for geojson data importing. If there is a name match
     * by a particular parent, we update the region record instead of creating a new one.
     *
     * @param regionDto input region dto object.
     * @return persisted/updated regionDto.
     */
    public RegionDto matchByName(RegionDto regionDto) {
        RegionDto result = null;

        if (!StringUtils.isBlank(regionDto.getName())) {
            String regionName = regionDto.getName().trim();

            if (regionDto.getParentIds() != null && regionDto.getParentIds().size() > 0) {
                Set<RegionJpa> parents = getEntityService().findRegionsByParent(regionDto.getParentIds());
                //going over parent regions
                for (RegionJpa parent : parents) {
                    Set<BaseJpaCompositeEntity> children = findDirectChildren(parent.getGuid());
                    for (BaseJpaCompositeEntity child : children) {
                        //we import only Regions
                        if (REGION.equals(child.getConverterType())) {

                            // if name matches this is our region
                            if (child.getName().equalsIgnoreCase(regionName)) {
                                // we are matching by name, so the Dto might not have guid/id and converter
                                // will generate new GUID which we do not want.
                                if (regionDto.getId() == null) {
                                    regionDto.setId(child.getGuid());
                                }
                                // get region dto with updated properties provided by regionDto
                                result = Convert.my(
                                        (IApplicationEntity) Convert.my(regionDto)
                                                .withJpa(child)
                                                .scope(Scope.ALL)
                                                .boom()
                                ).scope(Scope.ALL).boom();
                                break;
                            }
                        }
                    }
                    if (result != null) {
                        break;
                    }
                }
            } else {
                log.error("Region's parent ID not provided. Region name: {}", regionName);
            }
        } else {
            log.error("Region name can't be empty");
        }

        return result;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
