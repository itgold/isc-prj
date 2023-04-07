package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.IEntity;

/**
 * A contract for an alert concerning a specific entity.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IEntityAlert extends IApplicationEntity {

    IAlert getAlert();

    IEntity getEntity();

}
