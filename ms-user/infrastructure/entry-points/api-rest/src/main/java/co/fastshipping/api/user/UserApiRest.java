package co.fastshipping.api.user;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.user.mapper.UserRequestMapper;
import co.fastshipping.api.user.mapper.UserResponseMapper;
import co.fastshipping.api.user.request.CreateUserRequest;
import co.fastshipping.api.user.request.RegisterUserRequest;
import co.fastshipping.api.user.request.UpdateUserStatusRequest;
import co.fastshipping.api.user.response.UserResponse;
import co.fastshipping.usecase.user.CreateUserUseCase;
import co.fastshipping.usecase.user.GetDriverUsersUseCase;
import co.fastshipping.usecase.user.UpdateStatusUserUseCase;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_USER, version = ApiPath.V1)
public class UserApiRest {

    private final CreateUserUseCase createUserUseCase;
    private final GetDriverUsersUseCase getDriverUsersUseCase;
    private final UpdateStatusUserUseCase updateStatusUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseMapper.toResponse(createUserUseCase.execute(UserRequestMapper.toCreateUserCommand(request))));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseMapper.toResponse(createUserUseCase.execute(UserRequestMapper.toCreateUserCommand(request))));
    }

    @GetMapping("/driver")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseMapper.toResponse(getDriverUsersUseCase.execute()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,@Valid @RequestBody UpdateUserStatusRequest request) {
        updateStatusUserUseCase.execute(id, request.status());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
