package com.iscweb.component.web.auth.jwt.verifier;

import org.springframework.stereotype.Component;

/**
 * BloomFilterTokenVerifier.
 */
@Component
public class BloomFilterTokenVerifier implements ITokenVerifier {

    @Override
    public boolean verify(String jti) {
        return true;
    }
}
