package co.fastshipping.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {

        return route("ms-logistics")
                .route(path("/api/logistics/**"), http())
                .filter(lb("MS-LOGISTICS"))
                .build()

                .and(
                        route("ms-notification")
                                .route(path("/api/notification/**"), http())
                                .filter(lb("MS-NOTIFICATION"))
                                .build()
                )

                .and(
                        route("ms-shipping")
                                .route(path("/api/shipping/**"), http())
                                .filter(lb("MS-SHIPPING"))
                                .build()
                )

                .and(
                        route("ms-user")
                                .route(path("/api/user/**"), http())
                                .route(path("/api/auth/**"), http())
                                .filter(lb("MS-USER"))
                                .build()
                );
    }
}