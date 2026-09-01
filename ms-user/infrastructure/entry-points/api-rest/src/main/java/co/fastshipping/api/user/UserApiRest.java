package co.fastshipping.api.user;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.user.mapper.UserRequestMapper;
import co.fastshipping.api.user.mapper.UserResponseMapper;
import co.fastshipping.api.user.request.CreateUserRequest;
import co.fastshipping.api.user.response.UserResponse;
import co.fastshipping.usecase.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_USER, version = ApiPath.V1)
public class UserApiRest {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseMapper.toResponse(createUserUseCase.execute(UserRequestMapper.toCreateUserCommand(request))));
    }
}
