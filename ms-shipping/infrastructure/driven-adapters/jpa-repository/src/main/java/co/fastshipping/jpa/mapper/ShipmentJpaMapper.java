package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.ShipmentJpaEntity;
import co.fastshipping.model.shipment.Shipment;

public class ShipmentJpaMapper {

    private ShipmentJpaMapper() {
    }

    public static Shipment toDomain(ShipmentJpaEntity entity) {
        if (entity == null) return null;

        return Shipment.restore(
                entity.getId(),
                entity.getSenderAddressId(),
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }

    public static ShipmentJpaEntity toJpaEntity(Shipment domain) {
        if (domain == null) return null;

        return ShipmentJpaEntity.builder()
                .id(domain.getId())
                .senderAddressId(domain.getSenderAddressId())
                .createdAt(domain.getCreatedAt())
                .status(domain.getStatus())
                .build();
    }
}
