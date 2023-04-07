package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;

/**
 * A contract for an object which associates an Index to an Integration.
 */
public interface IIntegrationIndex extends IApplicationEntity {

    IIntegration getIntegration();

    IIndex getIndex();

}
