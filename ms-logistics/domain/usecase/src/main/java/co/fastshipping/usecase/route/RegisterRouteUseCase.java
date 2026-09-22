package co.fastshipping.usecase.route;

import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStop;
import co.fastshipping.model.route.exception.RouteAlreadyExistsException;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.usecase.route.command.RegisterRouteCommand;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RegisterRouteUseCase {

    private final RouteRepository routeRepository;

    public Route execute(RegisterRouteCommand command) {
        if (routeRepository.existsByName(command.name())) {
            throw new RouteAlreadyExistsException(
                    "A route with name " + command.name() + " already exists"
            );
        }

        LinkedHashSet<RouteStop> stops = command.stops()
                .stream()
                .map(stop -> RouteStop.create(null, stop.sequence(), stop.city()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return routeRepository.save(Route.create(command.name(), stops));
    }
}
