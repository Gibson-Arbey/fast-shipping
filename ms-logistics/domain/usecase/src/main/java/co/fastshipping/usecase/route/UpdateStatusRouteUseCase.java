package co.fastshipping.usecase.route;

import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.exception.RouteNotFoundException;
import co.fastshipping.model.route.gateways.RouteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStatusRouteUseCase {

    private final RouteRepository routeRepository;

    public void execute(Long id, String status) {
        if (!routeRepository.existsById(id)) {
            throw new RouteNotFoundException("Route not found: " + id);
        }

        routeRepository.updateStatus(id, RouteStatus.fromString(status));
    }
}
