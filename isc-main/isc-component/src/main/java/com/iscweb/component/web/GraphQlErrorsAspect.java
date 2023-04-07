package com.iscweb.component.web;

import com.iscweb.component.web.controller.graphql.common.GraphQlExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GraphQlErrorsAspect {

    /**
     * Matches all beans that implement {@link graphql.kickstart.tools.GraphQLResolver} as
     * {@code UserMutation}, {@code UserQuery}.
     */
    @Pointcut("target(graphql.kickstart.tools.GraphQLResolver)")
    private void allGraphQlResolverMethods() {}

    /**
     * Primary method executed around RestController methods which are annotated with {@code @RequestMapping}.
     *
     * @param joinPoint Joint point.
     */
    @Around(value = "allGraphQlResolverMethods()")
    public Object aroundPublicApiControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Unable to execute GraphQL operation", e);
            throw new GraphQlExecutionException("Unable to execute GraphQL operation", e);
        }
    }
}
