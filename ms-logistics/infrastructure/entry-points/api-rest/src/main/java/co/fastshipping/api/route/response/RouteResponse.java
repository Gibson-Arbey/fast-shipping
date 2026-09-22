package co.fastshipping.api.route.response;

import java.util.Set;

public record RouteResponse(
        Long id,
        String name,
        String status,
        Set<RouteStopResponse> stops
) {
}
