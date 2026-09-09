package co.fastshipping.security.adapter;

public record UserAuthentication(Long userId, String email, String role) {
}
