package com.iscweb.common.service;

import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ICompositeCache {

    /**
     * Get Composite tree root node.
     *
     * @return region composite implementation instance.
     */
    IRegionComposite getRootNode();

    /**
     * Find composite tree node by id.
     *
     * @param nodeId node id to lookup by.
     * @return Requested region composite instance.
     */
    IRegionComposite getNode(String nodeId);

    /**
     * Find nodes by ids.
     *
     * @param nodeIds Node ids
     * @return List of composite nodes found
     */
    List<IRegionComposite> findNodes(Collection<String> nodeIds);

    /**
     * Invalidate and reset cache into new composite tree.
     *
     * @param root node to invalidate cache for.
     */
    void resetCache(IRegionComposite root);

    /**
     * Update cache with new created composite node.
     *
     * @param entity composite entry to add to the existing cache.
     */
    void refreshCreated(IRegionComposite entity);

    /**
     * Update cache with updated composite node.
     *
     * @param entity composite entry to be updated in the cache.
     */
    void refreshUpdated(IRegionComposite entity);

    /**
     * Update cache with delete composite node.
     *
     * @param nodeId node that was deleted from the system to be reflected in the cache.
     */
    void refreshDeleted(String nodeId);

    /**
     * Clones composite subtree.
     *
     * @param compositeId Root composite node id
     * @param filter Filter to apply
     * @param cloner Helper callback to delegate composite node creation
     *
     * @return Cloned composite node
     */
    IRegionComposite cloneComposite(String compositeId, CompositeFilter filter, CompositeNodeCloner cloner);

    Collection<IRegionComposite> findChildren(Collection<IRegionComposite> roots, CompositeFilter filter, CompositeNodeCloner cloner);

    /**
     * Find all child regions by parents chain path.
     *
     * @param parentPath Parents chain path
     * @return List of all children regions matching parent path
     */
    Set<String> findChildRegionsByParentPath(String parentPath);

    /**
     * Helper callback interface to delegate composite nodes cloning.
     */
    interface CompositeNodeCloner {
        IRegionComposite clone(IRegionComposite rootNode);
    }
}
