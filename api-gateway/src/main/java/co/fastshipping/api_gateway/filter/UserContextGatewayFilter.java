package co.fastshipping.api_gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public class UserContextGatewayFilter {
    public static HandlerFilterFunction<ServerResponse, ServerResponse> filter() {

        return (request, next) -> {
            ServerRequest modifiedRequest =
                    ServerRequest.from(request)
                            .headers(headers -> {
                                String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
                                headers.remove(HttpHeaders.AUTHORIZATION);
                                if (authorization != null) {
                                    headers.set(HttpHeaders.AUTHORIZATION, authorization);
                                }
                            })
                            .build();

            return next.handle(modifiedRequest);
        };
    }
}
