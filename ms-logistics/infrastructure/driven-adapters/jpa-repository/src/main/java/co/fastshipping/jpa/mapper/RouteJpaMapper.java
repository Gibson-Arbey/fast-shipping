package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.RouteJpaEntity;
import co.fastshipping.jpa.entity.RouteStopJpaEntity;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStop;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public final class RouteJpaMapper {

    private RouteJpaMapper() {
    }

    public static RouteJpaEntity toEntity(Route route) {
        if (route == null) {
            return null;
        }

        RouteJpaEntity entity = RouteJpaEntity.builder()
                .id(route.getId())
                .name(route.getName())
                .status(route.getStatus())
                .stops(new LinkedHashSet<>())
                .build();

        route.getStops().forEach(stop -> entity.getStops().add(
                RouteStopJpaEntity.builder()
                        .id(stop.getId())
                        .route(entity)
                        .sequence(stop.getSequence())
                        .city(stop.getCity())
                        .build()
        ));

        return entity;
    }

    public static Route toDomain(RouteJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        LinkedHashSet<RouteStop> stops = entity.getStops()
                .stream()
                .map(stop -> RouteStop.restore(
                        stop.getId(),
                        entity.getId(),
                        stop.getSequence(),
                        stop.getCity()
                ))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return Route.restore(
                entity.getId(),
                entity.getName(),
                stops,
                entity.getStatus()
        );
    }
}
