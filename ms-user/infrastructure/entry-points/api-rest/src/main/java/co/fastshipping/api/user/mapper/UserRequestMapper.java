package co.fastshipping.api.user.mapper;

import co.fastshipping.api.user.request.CreateUserRequest;
import co.fastshipping.usecase.user.command.CreateUserCommand;

public class UserRequestMapper {

    public static CreateUserCommand toCreateUserCommand(CreateUserRequest request) {
        return new CreateUserCommand(
            request.name(),
            request.lastName(),
            request.email(),
            request.password()
        );

    }
}
