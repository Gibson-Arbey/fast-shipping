package co.fastshipping.api_gateway.model;

public record UserAuthentication(Long userId, String email, String role) {
}
