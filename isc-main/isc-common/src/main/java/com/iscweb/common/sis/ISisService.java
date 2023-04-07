package com.iscweb.common.sis;

/**
 * Marker interface for all Integration services which are generated using SiS (Simple Integration Service) framework.
 * Each service defined as interface and have annotated methods.
 *
 * <pre>{@code
 * public interface IExampleIntegrationService extends ISisService {
 *
 *     @SisServiceMethod("MethodNameWhenSerialized")
 *     ResponseDto someMethodWithSingleParam(RequestDto request);
 *
 *     ResponseDto someMethodWithMultipleParams(@SisServiceMethodParam("NameWhenSerialized") String param1, Long param2);
 * }
 * </pre>
 * The serialization and how the annotations are treated depends on specific integration implementation.
 */
public interface ISisService {
}
