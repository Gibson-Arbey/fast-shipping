package co.fastshipping.api.authentication.response;

public record AuthenticationResponse(
        Long id,
        String email,
        String username,
        String roleName,
        String token
) {
}
