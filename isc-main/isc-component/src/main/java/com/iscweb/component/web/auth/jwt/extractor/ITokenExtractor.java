package com.iscweb.component.web.auth.jwt.extractor;

/**
 * Implementations should return a raw, base-64 encoded representation of a JWT Token.
 */
public interface ITokenExtractor {

    String extract(String payload);
}
