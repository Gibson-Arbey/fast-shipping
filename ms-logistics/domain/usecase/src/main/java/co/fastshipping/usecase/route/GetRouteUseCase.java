package co.fastshipping.usecase.route;

import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.usecase.route.query.GetRouteQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRouteUseCase {

    private final RouteRepository routeRepository;

    public List<Route> execute(GetRouteQuery query) {
        return routeRepository.findAllByFilters(
                RouteStatus.fromString(query.status()),
                query.name()
        );
    }
}
