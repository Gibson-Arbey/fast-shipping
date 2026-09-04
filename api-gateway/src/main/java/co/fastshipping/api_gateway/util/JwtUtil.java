package co.fastshipping.api_gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public Long extractUserId(DecodedJWT jwt) {
        return jwt.getClaim("userId").asLong();
    }

    public String extractRole(DecodedJWT jwt) {
        return jwt.getClaim("role").asString();
    }

    public String extractUsername(DecodedJWT jwt) {
        return jwt.getSubject();
    }
}
