package co.fastshipping.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final SecurityConstant securityConstant;
    private final Algorithm algorithm;

    public JwtUtil(SecurityConstant securityConstant) {
        this.securityConstant = securityConstant;
        this.algorithm = Algorithm.HMAC256(securityConstant.getJwtKeyPrivate());
    }

    public String createToken(String email, Long userId, String role) {
        return JWT.create()
                .withIssuer(securityConstant.getJwtUserGenerator())
                .withSubject(email)
                .withClaim("userId", userId)
                .withClaim("role", "ROLE_" + role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + securityConstant.getJwtExpiration()))
                .sign(algorithm);
    }

}