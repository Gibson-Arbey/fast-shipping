package co.fastshipping.model.authentication.gateways;

public interface AuthenticationTokenRepository {

    String generateToken(Long userId, String email, String role);
}
