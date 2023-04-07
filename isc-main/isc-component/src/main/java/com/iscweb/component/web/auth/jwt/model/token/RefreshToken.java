package com.iscweb.component.web.auth.jwt.model.token;

import com.iscweb.component.web.auth.jwt.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * RefreshToken.
 */
@SuppressWarnings("unchecked")
public class RefreshToken implements IJwtToken {

    @Getter
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates a Refresh Token.
     *
     * @param token Raw Token to be used to create the Refresh Token.
     * @param signingKey Signing Key to be used to create the Refresh Token.
     *
     * @return a new Refresh Token
     */
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);
        List<String> scopes = claims.getBody().get("scopes", List.class);

        Optional<RefreshToken> result = Optional.empty();
        if (scopes != null && !scopes.isEmpty()
                && scopes.stream().anyMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            result = Optional.of(new RefreshToken(claims));
        }

        return result;
    }

    @Override
    public String getToken() {
        return null;
    }

    public String getJti() {
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
