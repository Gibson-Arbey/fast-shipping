package co.fastshipping.api.authentication;

import co.fastshipping.api.authentication.mapper.AuthenticationResponseMapper;
import co.fastshipping.api.authentication.request.AuthenticationRequest;
import co.fastshipping.api.authentication.response.AuthenticationResponse;
import co.fastshipping.api.config.ApiPath;
import co.fastshipping.usecase.authentication.AuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_AUTH, version =  ApiPath.V1)
public class AuthApiRest {

    private final AuthenticationUseCase authenticationUseCase;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(AuthenticationResponseMapper.toResponse(authenticationUseCase.execute(request.email(), request.password())));
    }
}
