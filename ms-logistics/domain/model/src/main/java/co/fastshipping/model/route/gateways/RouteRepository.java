package co.fastshipping.model.route.gateways;

import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;

import java.util.List;

public interface RouteRepository {

    Route save(Route route);

    List<Route> findAllByFilters(RouteStatus status, String name);

    Route findById(Long id);

    void updateStatus(Long id, RouteStatus status);

    boolean existsById(Long id);

    boolean existsByName(String name);
}
