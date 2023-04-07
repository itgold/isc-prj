package com.iscweb.component.api.handler.v1;

import com.iscweb.service.api.IPublicApiContract;


/**
 * This interface defines the signatures of the methods that should be used to handle
 * requests to v10 of the public API.  It has two implementations: {@link IPublicApiContractV10}
 * and {@link PublicApiActionHandlerV10}.  By convention, each method in {@link IPublicApiContractV10}
 * should simply delegate to the corresponding method in {@link PublicApiActionHandlerV10}.
 */
public interface IPublicApiContractV10 extends IPublicApiContract {

}
