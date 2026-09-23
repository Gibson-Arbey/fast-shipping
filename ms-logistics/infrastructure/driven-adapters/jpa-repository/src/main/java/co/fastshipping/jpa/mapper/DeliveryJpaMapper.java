package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.DeliveryJpaEntity;
import co.fastshipping.model.delivery.Delivery;

public final class DeliveryJpaMapper {
    private DeliveryJpaMapper() { }

    public static DeliveryJpaEntity toEntity(Delivery delivery) {
        if (delivery == null) return null;
        return DeliveryJpaEntity.builder()
                .id(delivery.getId())
                .parcelId(delivery.getParcelId())
                .routeAssignmentId(delivery.getRouteAssignmentId())
                .routeStopId(delivery.getRouteStopId())
                .status(delivery.getStatus())
                .build();
    }

    public static Delivery toDomain(DeliveryJpaEntity entity) {
        if (entity == null) return null;
        return Delivery.restore(entity.getId(), entity.getParcelId(), entity.getRouteAssignmentId(), entity.getRouteStopId(), entity.getStatus());
    }
}
