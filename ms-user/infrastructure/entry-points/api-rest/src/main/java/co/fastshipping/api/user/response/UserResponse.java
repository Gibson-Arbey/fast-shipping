package co.fastshipping.api.user.response;

public record UserResponse(String name, String lastName, String email,  Long roleId, String status) {
}
