package co.fastshipping.api.route.mapper;

import co.fastshipping.api.route.response.RouteResponse;
import co.fastshipping.api.route.response.RouteStopResponse;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStop;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public final class RouteResponseMapper {

    private RouteResponseMapper() {
    }

    public static RouteResponse toResponse(Route route) {
        if (route == null) {
            return null;
        }

        LinkedHashSet<RouteStopResponse> stops = route.getStops()
                .stream()
                .sorted(java.util.Comparator.comparing(RouteStop::getSequence))
                .map(stop -> new RouteStopResponse(
                        stop.getId(),
                        stop.getSequence(),
                        stop.getCity()
                ))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new RouteResponse(
                route.getId(),
                route.getName(),
                route.getStatus().name(),
                stops
        );
    }

    public static List<RouteResponse> toResponse(List<Route> routes) {
        return routes.stream()
                .map(RouteResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
