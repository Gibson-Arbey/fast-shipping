package co.fastshipping.api.user.mapper;

import co.fastshipping.api.user.response.UserResponse;
import co.fastshipping.model.user.User;

public class UserResponseMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
            user.getName(),
            user.getLastName(),
            user.getEmail().value(),
            user.getRoleId(),
            user.getStatus().name()
        );
    }
}
