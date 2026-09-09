package co.fastshipping.api.filter;

public record UserAuthentication(Long userId, String email, String role) {
}
