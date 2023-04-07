package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.IEntity;

/**
 * A contract for a tag associated with a specific entity.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IEntityTag extends IApplicationEntity {

    ITag getTag();

    IEntity getEntity();

}
