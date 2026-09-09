package co.fastshipping.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class JwtUtil {

    private final SecurityConstant securityConstant;
    private final Algorithm algorithm;

    public JwtUtil(SecurityConstant securityConstant) {
        this.securityConstant = securityConstant;
        this.algorithm = Algorithm.HMAC256(securityConstant.getJwtKeyPrivate());
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer(securityConstant.getJwtUserGenerator())
                .build()
                .verify(token);
    }

    public JwtClaims extractClaims(DecodedJWT jwt) {
        Long userId = jwt.getClaim("userId").asLong();
        String email = jwt.getSubject();
        String role = jwt.getClaim("role").asString();

        if (userId == null || userId <= 0 || email == null || email.isBlank() || role == null || role.isBlank()) {
            throw new IllegalArgumentException("JWT is missing required identity claims");
        }

        return new JwtClaims(userId, email, normalizeRole(role));
    }

    private String normalizeRole(String role) {
        String normalizedRole = role.trim().toUpperCase(Locale.ROOT);
        return normalizedRole.startsWith("ROLE_")
                ? normalizedRole
                : "ROLE_" + normalizedRole;
    }

    public record JwtClaims(Long userId, String email, String role) {
    }

}
