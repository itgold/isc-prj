package com.iscweb.common.model;

import org.slf4j.Logger;

/**
 * An interface for major application components, such as Controllers and Services.
 */
public interface IApplicationComponent {

    /**
     * Template method for getting a reference to the logger of the specific class.
     * It is used for logging invocations by particular component methods..
     *
     * @return logger object instance.
     */
    Logger getLogger();

}
