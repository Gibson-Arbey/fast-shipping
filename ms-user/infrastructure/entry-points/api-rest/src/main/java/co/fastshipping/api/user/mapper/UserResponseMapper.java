package co.fastshipping.api.user.mapper;

import co.fastshipping.api.user.response.UserResponse;
import co.fastshipping.model.user.User;

import java.util.List;

public class UserResponseMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return  null;
        }
        return new UserResponse(
            user.getName(),
            user.getLastName(),
            user.getEmail().value(),
            user.getRoleId(),
            user.getStatus().name()
        );
    }

    public static List<UserResponse> toResponse(List<User> users) {
        return users.stream().map(UserResponseMapper::toResponse).toList();
    }
}
