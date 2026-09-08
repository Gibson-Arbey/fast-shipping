package co.fastshipping.api_gateway.filter;

import co.fastshipping.api_gateway.model.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public class UserContextGatewayFilter {

    public static HandlerFilterFunction<ServerResponse, ServerResponse> filter() {

        return (request, next) -> {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();


            if (authentication == null ||
                    !authentication.isAuthenticated()) {

                return next.handle(request);
            }

            UserAuthentication user =
                    (UserAuthentication) authentication.getPrincipal();

            assert user != null;
            ServerRequest modifiedRequest =
                    ServerRequest.from(request)
                            .header("X-User-Id", user.userId().toString())
                            .header("X-User-Email", user.email())
                            .header("X-User-Role", user.role())
                            .build();

            return next.handle(modifiedRequest);
        };
    }
}