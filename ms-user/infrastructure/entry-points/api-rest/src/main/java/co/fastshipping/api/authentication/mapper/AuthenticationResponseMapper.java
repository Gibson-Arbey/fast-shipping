package co.fastshipping.api.authentication.mapper;

import co.fastshipping.api.authentication.response.AuthenticationResponse;
import co.fastshipping.model.authentication.Authentication;

public class AuthenticationResponseMapper {

    public static AuthenticationResponse toResponse(Authentication authentication) {
        return new AuthenticationResponse(
                authentication.getUserId(),
                authentication.getEmail(),
                authentication.getUsername(),
                authentication.getRole(),
                authentication.getToken()
        );
    }
}
