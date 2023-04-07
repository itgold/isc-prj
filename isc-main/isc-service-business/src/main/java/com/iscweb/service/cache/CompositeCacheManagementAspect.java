package com.iscweb.service.cache;

import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.service.composite.CompositeService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.iscweb.common.annotations.CompositeCacheEntry.UpdateType.CREATE;
import static com.iscweb.common.annotations.CompositeCacheEntry.UpdateType.DELETE;
import static com.iscweb.common.annotations.CompositeCacheEntry.UpdateType.UPDATE;

/**
 * An aspect for handling cacheable requests to collections of entities. It is used as a wrapper around
 * method invocation and tries to return data from the cache instead of making a call to heavy resource.
 *
 * @author dmorozov
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class CompositeCacheManagementAspect {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    @Pointcut("@annotation(compositeCacheEntry)")
    public void compositeCacheEntryAnnotation(CompositeCacheEntry compositeCacheEntry) {
    }

    /**
     * CompositeCacheEntry annotation handler.
     *
     * @param joinPoint           method join point.
     * @param compositeCacheEntry annotation configuration object.
     * @return the result of method execution.
     * @throws Throwable if operation failed.
     */
    @Around(value = "compositeCacheEntryAnnotation(compositeCacheEntry)",
            argNames = "joinPoint, compositeCacheEntry")
    public Object collectionCacheableHandler(ProceedingJoinPoint joinPoint, CompositeCacheEntry compositeCacheEntry) throws Throwable {
        Object result = joinPoint.proceed();

        if (compositeCacheEntry.value() == CREATE || compositeCacheEntry.value() == UPDATE) {

            if (result instanceof BaseSchoolEntityDto) {
                getCompositeService().refreshNode((BaseSchoolEntityDto) result, compositeCacheEntry.value());
            } else {
                log.warn("Violated @CompositeCacheEntry annotation contract. Unexpected {} return type!",
                        result != null ? result.getClass().getSimpleName() : "null");
            }
        } else if (compositeCacheEntry.value() == DELETE) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0 && args[0] instanceof String) {
                getCompositeService().refreshDeleted((String) args[0]);
            } else {
                log.warn("Violated @CompositeCacheEntry annotation contract. Unexpected {} first parameters type. Should be GUID string!",
                        args != null && args.length > 0 ? args[0].getClass().getSimpleName() : "null");
            }
        } else {
            log.warn("Unhandled {} cache operation!", compositeCacheEntry.value());
        }

        return result;
    }
}
