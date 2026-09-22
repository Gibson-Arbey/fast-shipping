package co.fastshipping.model.route;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Route {

    private final Long id;

    private final String name;

    private final Set<RouteStop> stops;

    private final RouteStatus status;

    private Route(Long id, String name, Set<RouteStop> stops, RouteStatus status) {
        if (name == null || name.isBlank()) {
            throw new InvalidFieldException("Route name is not valid");
        }
        if (stops == null || stops.size() < 2 || stops.stream().anyMatch(java.util.Objects::isNull)) {
            throw new InvalidFieldException("A route must have at least two stops");
        }
        if (stops.stream().map(RouteStop::getSequence).collect(Collectors.toSet()).size() != stops.size()) {
            throw new InvalidFieldException("Route stop sequences must be unique");
        }
        if (status == null) {
            throw new InvalidFieldException("Route status cannot be null");
        }

        this.id = id;
        this.name = name.trim();
        this.stops = new LinkedHashSet<>(stops);
        this.status = status;
    }

    public static Route create(String name, Set<RouteStop> stops) {
        return new Route(null, name, stops, RouteStatus.ACTIVE);
    }

    public static Route create(String name, Set<RouteStop> stops, RouteStatus status) {
        return new Route(null, name, stops, status);
    }

    public static Route restore(Long id, String name, Set<RouteStop> stops, RouteStatus status) {
        return new Route(id, name, stops, status);
    }
}
