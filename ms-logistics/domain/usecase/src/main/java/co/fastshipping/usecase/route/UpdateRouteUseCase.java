package co.fastshipping.usecase.route;

import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStop;
import co.fastshipping.model.route.exception.RouteAlreadyExistsException;
import co.fastshipping.model.route.exception.RouteNotFoundException;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.usecase.route.command.RegisterRouteCommand;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UpdateRouteUseCase {

    private final RouteRepository routeRepository;

    public Route execute(Long id, RegisterRouteCommand command) {
        Route current = routeRepository.findById(id);
        if (current == null) {
            throw new RouteNotFoundException("Route not found: " + id);
        }

        if (!current.getName().equalsIgnoreCase(command.name()) && routeRepository.existsByName(command.name())) {
            throw new RouteAlreadyExistsException("A route with name " + command.name() + " already exists");
        }

        LinkedHashSet<RouteStop> stops = command.stops().stream()
                .map(stop -> RouteStop.create(null, stop.sequence(), stop.city()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return routeRepository.save(Route.restore(id, command.name(), stops, current.getStatus()));
    }
}
