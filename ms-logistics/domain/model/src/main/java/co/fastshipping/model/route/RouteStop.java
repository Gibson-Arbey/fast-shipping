package co.fastshipping.model.route;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class RouteStop {

    private final Long id;

    private final Long routeId;

    private final Integer sequence;

    private final String city;

    private RouteStop(Long id, Long routeId, Integer sequence, String city, boolean persisted) {
        if (persisted && routeId == null) {
            throw new InvalidFieldException("routeId is null");
        }
        if (sequence == null || sequence <= 0) {
            throw new InvalidFieldException("Sequence is not valid");
        }
        if (city == null || city.isBlank()) {
            throw new InvalidFieldException("City is not valid");
        }

        this.id = id;
        this.routeId = routeId;
        this.sequence = sequence;
        this.city = city.trim();
    }

    public static RouteStop create(Long routeId, Integer sequence, String city) {
        return new RouteStop(null, routeId, sequence, city, false);
    }

    public static RouteStop restore(Long id, Long routeId, Integer sequence, String city) {
        return new RouteStop(id, routeId, sequence, city, true);
    }
}
