package co.fastshipping.api.route.mapper;

import co.fastshipping.api.route.request.RegisterRouteRequest;
import co.fastshipping.usecase.route.command.RegisterRouteCommand;
import co.fastshipping.usecase.route.command.RegisterRouteStopCommand;
import co.fastshipping.usecase.route.query.GetRouteQuery;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public final class RouteRequestMapper {

    private RouteRequestMapper() {
    }

    public static RegisterRouteCommand toCommand(RegisterRouteRequest request) {
        if (request == null) {
            return null;
        }

        return new RegisterRouteCommand(
                request.name(),
                request.stops()
                        .stream()
                        .map(stop -> new RegisterRouteStopCommand(stop.sequence(), stop.city()))
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    public static GetRouteQuery toQuery(String status, String name) {
        return new GetRouteQuery(status, name);
    }
}
