package co.fastshipping.model.authentication.gateways;

public interface AuthenticationTokenValidator {

    AuthenticatedUser validate(String token);

    record AuthenticatedUser(Long userId, String email, String role) {
    }
}
