package co.fastshipping.security.adapter;

import co.fastshipping.model.authentication.gateways.AuthenticationTokenRepository;
import co.fastshipping.model.authentication.gateways.AuthenticationTokenValidator;
import co.fastshipping.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenAdapter implements AuthenticationTokenRepository, AuthenticationTokenValidator {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(Long userId, String email, String role) {
        return jwtUtil.createToken(email, userId, role);
    }

    @Override
    public AuthenticatedUser validate(String token) {
        var claims = jwtUtil.extractClaims(jwtUtil.validateToken(token));
        return new AuthenticatedUser(claims.userId(), claims.email(), claims.role());
    }
}
