package co.fastshipping.security.adapter;

import co.fastshipping.model.authentication.gateways.AuthenticationTokenRepository;
import co.fastshipping.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenAdapter implements AuthenticationTokenRepository {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(Long userId, String email, String role) {
        return jwtUtil.createToken(email, userId, role);
    }
}
