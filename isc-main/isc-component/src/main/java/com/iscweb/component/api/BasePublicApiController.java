package com.iscweb.component.api;

import com.iscweb.component.web.controller.BaseRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base class for public REST API controllers. Children of this class are usually different versions
 * of API controllers that we support whose purpose is to provide REST endpoints for the specific API version.
 * <p>
 * To maintain backwards compatibility and keep common functionality in the same place, BasePublicApiController
 * holds all common methods and functions available across different versions that then might be called and reused
 * from the children controllers by calling super() methods. Version controllers don't have to invoke super functions
 * or can provide version specific implementations if public API functions are different.
 * <p>
 * Rationale: this class is not declared as a Spring controller and doesn't define any RequestMappings because
 * it gets extended by real controllers that declare their own version-specific mappings. In order
 * to avoid mapping clashes, every child must declare a mapping within its scope and then call super
 * method from this controller if necessary for re-usability.
 * <p>
 * /api/* endpoints should be handled by the most recent version of the controller so our customers who want to stay
 * on the most recent version don't have to update the URL every time.
 * <p>
 * /api/x/* where x is the major API version should be implemented by a controller with the most recent minor version
 * for version x.
 *
 * @param <T> Is an Action Handler which is linked to a Service.
 *
 * @author skurenkov
 * @see BasePublicApiActionHandler
 */
@RestController
public abstract class BasePublicApiController<T extends BasePublicApiActionHandler> extends BaseRestController<T> {

    /**
     * Just a simple ping-pong api endpoint: /ping.
     *
     * @return pong string
     * @since 1.0
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseBody
    public String pingRequestHandler() {
        StringBuilder result = new StringBuilder().append("pong");
        double rnd = Math.random();
        if (rnd > 0.8) {
            result.append("!");
        }
        if (rnd > 0.9) {
            result.append("!");
        }
        if (rnd < 0.1) {
            result.append(". Isn't it fun?");
        }
        return result.append("\n").toString();
    }

    /**
     * A /version endpoint that returns the current API version.
     *
     * @return current API version
     * @since 1.0
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public String versionRequestHandler() {
        return getVersion();
    }

    /**
     * A template method that delegates the API version retrieval from the current API controller.
     *
     * @return current API version
     */
    protected String getVersion() {
        return getService().getVersion();
    }

}
