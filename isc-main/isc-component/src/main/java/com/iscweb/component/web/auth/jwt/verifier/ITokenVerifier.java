package com.iscweb.component.web.auth.jwt.verifier;

public interface ITokenVerifier {

    boolean verify(String jti);
}
