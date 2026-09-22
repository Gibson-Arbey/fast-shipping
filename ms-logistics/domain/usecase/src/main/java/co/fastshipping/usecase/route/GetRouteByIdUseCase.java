package co.fastshipping.usecase.route;

import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.exception.RouteNotFoundException;
import co.fastshipping.model.route.gateways.RouteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRouteByIdUseCase {

    private final RouteRepository routeRepository;

    public Route execute(Long id) {
        Route route = routeRepository.findById(id);
        if (route == null) {
            throw new RouteNotFoundException("Route not found: " + id);
        }

        return route;
    }
}
