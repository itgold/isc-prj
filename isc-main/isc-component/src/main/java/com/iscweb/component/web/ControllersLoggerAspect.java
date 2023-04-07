package com.iscweb.component.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.exception.BaseApplicationException;
import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.common.log.ComponentLogLevel;
import com.iscweb.common.log.NoLog;
import com.iscweb.common.model.IApplicationComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An aspect used for logging accesses to application controller methods.
 */
@Slf4j
@Aspect
@Component
public class ControllersLoggerAspect {

    public static final String EMPTY_PARAMS = "";

    /**
     * Log pattern for external JSON API that we expose to our customers.
     * userName, requestMethods, mappingValue, annotatedParams
     */
    public static final String API_LOG_PATTERN = "* API {}:{} user={} : {}({}) ::> {} - {} ms";

    /**
     * Log pattern for internal API controllers which is used in the front-end.
     */
    public static final String REST_LOG_PATTERN = "* REST {}:{} user={} : {}({}) ::> {} - {} ms";

    /**
     * Log pattern for regular web requests for application or HTML content.
     * <userName> - # WEB [request method] {mapping value} : {annotatedParams} {time} ms ::> login-page
     */
    public static final String WEB_LOG_PATTERN = "# WEB {}:{} user={} : {}({}) ::> {} - {} ms";

    /**
     * Defines any class or method within the public API controller package.
     */
    @Pointcut("execution(* com.iscweb..api.controller..*.*(..))")
    public void publicApiControllerPackageMethods() {
    }

    /**
     * Defines any class or method within the controller package, excluding API controllers.
     */
    @Pointcut("execution(* com.iscweb..web.controller.rest..*.*(..))")
    public void webRestControllerPackageMethods() {
    }

    /**
     * Defines any class or method within the controller package, excluding API controllers.
     */
    @Pointcut("execution(* com.iscweb..web.controller.base..*.*(..))")
    public void webBaseControllerPackageMethods() {
    }

    /**
     * Defines any method annotated with the RequestMapping annotation.
     *
     * @param requestMapping Spring's request mapping annotation.
     */
    @Pointcut("@annotation(requestMapping)")
    public void requestMappingAnnotation(RequestMapping requestMapping) {
    }

    /**
     * Defines classes annotated with the RestController annotation.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    /**
     * Defines classes annotated with the Controller annotation.
     */
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void baseController() {
    }

    /**
     * Primary method executed around RestController methods annotated with RequestMapping.
     *
     * @param requestMapping request mapping annotation.
     */
    @Around(value = "publicApiControllerPackageMethods() && restController() && requestMappingAnnotation(requestMapping)",
            argNames = "joinPoint, requestMapping")
    public Object aroundPublicApiControllerMethod(ProceedingJoinPoint joinPoint,
                                                  RequestMapping requestMapping) throws Throwable {

        return handleWebRequest(joinPoint, requestMapping, API_LOG_PATTERN);
    }

    /**
     * Primary method executed around RestController methods annotated with RequestMapping.
     *
     * @param requestMapping request mapping annotation.
     */
    @Around(value = "webRestControllerPackageMethods() && restController() && requestMappingAnnotation(requestMapping)",
            argNames = "joinPoint, requestMapping")
    public Object aroundRestControllerMethod(ProceedingJoinPoint joinPoint,
                                             RequestMapping requestMapping) throws Throwable {

        return handleWebRequest(joinPoint, requestMapping, REST_LOG_PATTERN);
    }

    /**
     * Primary method executed around BaseController methods annotated with RequestMapping.
     *
     * @param joinPoint      aspect join point.
     * @param requestMapping request mapping annotation.
     */
    @Around(value = "webBaseControllerPackageMethods() && baseController() && requestMappingAnnotation(requestMapping)",
            argNames = "joinPoint, requestMapping")
    public Object aroundBaseControllerMethod(ProceedingJoinPoint joinPoint,
                                             RequestMapping requestMapping) throws Throwable {

        return handleWebRequest(joinPoint, requestMapping, WEB_LOG_PATTERN);
    }

    /**
     * Handles any type of request and logs it with the provided pattern.
     *
     * @param joinPoint      join point to execute.
     * @param requestMapping target method web mapping.
     * @param logPattern     log pattern for logging.
     * @return the result of execution.
     * @throws Throwable if something goes wrong logs it and then throws this thing out of here.
     */
    private Object handleWebRequest(ProceedingJoinPoint joinPoint,
                                    RequestMapping requestMapping,
                                    String logPattern) throws Throwable {
        Object result = null;
        Throwable error = null;
        LogEntryData logEntry = createWebResourceLogEntry(joinPoint, requestMapping).pattern(logPattern);

        try {
            result = joinPoint.proceed();
            logEntry.returned(result);
        } catch (BaseApplicationException e) {
            logEntry.level(e.getErrorLogLevel())
                    .returned(e.getMessage());
            error = e;
        } catch (Throwable throwable) {
            logEntry.level(LogLevel.ERROR)
                    .returned("Error");
            error = throwable;
        } finally {
            logEntry.logIt();
            if (Objects.nonNull(error)) {
                boolean logError = true;
                if (BaseApplicationException.class.isAssignableFrom(error.getClass())) {
                    ErrorCode errorCode = ((BaseApplicationException) error).getErrorCode();
                    logError = errorCode.getHttpCode() >= 500;
                }
                if (logError) {
                    logEntry.getLogger().error("Application Error", error);
                }
                // If it is not an exception, but an error, wrap it so that Spring can digest it back.
                // This is infrequent, but it can happen with some errors such as dynamic type reloads.
                if (Error.class.isAssignableFrom(error.getClass())) {
                    error = new IllegalStateException("A throwable error has been intercepted, wrapping in exception", error);
                }
                throw error;
            }
        }

        return result;
    }

    /**
     * Extracts web entry information from the joinPoint and returns it as a LogEntryData.
     *
     * @param joinPoint      current execution joint point.
     * @param requestMapping current web request mapping.
     * @return log entry data object populated with the called method configuration.
     */
    private LogEntryData createWebResourceLogEntry(JoinPoint joinPoint, RequestMapping requestMapping) {
        LogEntryData result = new LogEntryData();
        Object targetObject = joinPoint.getTarget();
        if (targetObject instanceof IApplicationComponent) {
            IApplicationComponent applicationComponent = (IApplicationComponent) targetObject;

            String userName = "<>";
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                userName = authentication.getName();
            }

            String requestUri;
            String requestMethod;
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                requestUri = requestAttributes.getRequest().getRequestURI();
                requestMethod = requestAttributes.getRequest().getMethod();
            } else {
                requestUri = Arrays.toString(requestMapping.value());
                requestMethod = "NAN";
            }

            Method method = getInvokedMethod(joinPoint);
            LogLevel logLevel = getAnnotatedLogLevel(method);

            if (logLevel != LogLevel.OFF) {
                Object[] args = joinPoint.getArgs();
                if (args != null && args.length > 0) {
                    try {
                        Annotation[][] annotations = method.getParameterAnnotations();
                        Map<Object, Object> annotatedParams = extractAnnotatedParams(annotations, args);

                        result = LogEntryData.valueOf(applicationComponent.getLogger(), logLevel, requestMethod, requestUri, userName, method.getName(), annotatedParams);
                    } catch (Throwable e) {
                        log.warn("Failed to extract logging information for invoked controller " + applicationComponent);
                    }
                } else {
                    result = LogEntryData.valueOf(applicationComponent.getLogger(), logLevel, requestMethod, requestUri, userName, method.getName(), EMPTY_PARAMS);
                }
            }
        } else {
            throw new IllegalStateException("All controllers must implement IApplicationComponent interface. Please double-check " + targetObject + " implementation");
        }

        result.start();
        return result;
    }

    /**
     * Retrieves currently executed Method object from the joinPoint.
     *
     * @param joinPoint aspect join point.
     * @return method reflective reference.
     */
    private Method getInvokedMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * Retrieves log level from the ComponentLogLevel method annotation or use a default which is INFO.
     *
     * @param method method for annotation checking.
     * @return log level to be used for logging.
     */
    private LogLevel getAnnotatedLogLevel(Method method) {
        LogLevel result = LogLevel.INFO;
        ComponentLogLevel annotation = method.getAnnotation(ComponentLogLevel.class);
        if (annotation != null) {
            result = annotation.value();
        }
        return result;
    }

    /**
     * Extracts web related parameters from the method signature so it can be used in the log.
     *
     * @param annotations an array of method annotations.
     * @param args        an array of method parameter values.
     * @return a map of parameter name associated with the parameter value.
     */
    private Map<Object, Object> extractAnnotatedParams(Annotation[][] annotations, Object[] args) {
        Map<Object, Object> result = Maps.newHashMap();
        int annotationsLength = annotations.length;
        for (int i = 0; i < annotationsLength; i++) {
            Annotation[] argumentAnnotations = annotations[i];
            String paramName = null;
            boolean isIgnored = false;
            for (Annotation annotation : argumentAnnotations) {
                if (annotation instanceof NoLog) {
                    isIgnored = true;
                } else if (annotation instanceof RequestParam) {
                    paramName = ((RequestParam) annotation).value();
                } else if (annotation instanceof PathVariable) {
                    paramName = ((PathVariable) annotation).value();
                } else if (annotation instanceof RequestBody) {
                    paramName = "@RequestBody";
                }
            }
            if (paramName != null) {
                if (isIgnored) {
                    result.put(paramName, " <<< suppressed >>> ");
                } else {
                    result.put(paramName, args[i]);
                }
            }
        }
        return result;
    }

    /**
     * This class accumulates all the data needed for a single log line.
     * It contains information related to the user, invoked method and parameters, return value, etc.
     */
    private static class LogEntryData {

        @Getter
        private Logger logger;

        @Getter
        private LogLevel level;

        @Getter
        private String pattern;

        @Getter
        private List<Object> arguments;

        @Getter
        private Object returned;

        @Getter
        @Setter
        private StopWatch stopWatch = new StopWatch();

        public LogEntryData() {
            this.logger = log;
            this.level = LogLevel.OFF;
            this.arguments = Lists.newArrayList();
        }

        public LogEntryData(Logger logger,
                            LogLevel level,
                            Object... arguments) {

            this.logger = logger;
            this.level = level;
            this.arguments = Lists.newArrayList(arguments);
        }

        public static LogEntryData valueOf(Logger logger,
                                           LogLevel level,
                                           Object... arguments) {
            return new LogEntryData(logger, level, arguments);
        }

        public LogEntryData pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        @SuppressWarnings("rawtypes")
        public void returned(Object returnValue) {
            if (returnValue instanceof Collection) {
                this.returned = ((Collection) returnValue).size() + " items";
            } else if (returnValue instanceof Map) {
                this.returned = ((Map) returnValue).size() + " items";
            } else {
                this.returned = returnValue;
            }
        }

        public LogEntryData level(LogLevel level) {
            this.level = level;
            return this;
        }

        public void start() {
            getStopWatch().start();
        }

        /**
         * Logs out the final result using given logging level.
         */
        public void logIt() {
            getStopWatch().stop();
            if (Objects.nonNull(returned) && !returned.getClass().isArray()) {
                arguments.add(returned);
            }
            arguments.add(getStopWatch().getTotalTimeMillis());
            if (logger != null && level != null) {
                switch (level) {
                    case TRACE:
                        logger.trace(pattern, arguments.toArray());
                        break;
                    case DEBUG:
                        logger.debug(pattern, arguments.toArray());
                        break;
                    case INFO:
                        logger.info(pattern, arguments.toArray());
                        break;
                    case WARN:
                        logger.warn(pattern, arguments.toArray());
                        break;
                    case ERROR:
                        logger.error(pattern, arguments.toArray());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
