package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.ParcelJpaEntity;
import co.fastshipping.jpa.entity.ShipmentJpaEntity;
import co.fastshipping.model.parcel.Parcel;

public class ParcelJpaMapper {

    private ParcelJpaMapper() {
    }

    public static Parcel toDomain(ParcelJpaEntity entity) {
        if (entity == null) return  null;
        return Parcel.restore(
                entity.getId(),
                entity.getTrackingNumber(),
                entity.getDestinationAddressId(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getWidth(),
                entity.getLength(),
                entity.getClasificationTamanho(),
                entity.getType(),
                entity.getStatus(),
                entity.getDescription(),
                entity.getShipment() != null ? entity.getShipment().getId() : null
        );
    }

    public static ParcelJpaEntity toJpaEntity(Parcel domain) {
        if (domain == null) return null;
        return ParcelJpaEntity.builder()
                .id(domain.getId())
                .trackingNumber(domain.getTrackingNumber())
                .destinationAddressId(domain.getDestinationAddressId())
                .weight(domain.getWeight())
                .height(domain.getHeight())
                .width(domain.getWidth())
                .length(domain.getLength())
                .clasificationTamanho(domain.getClasificationTamanho())
                .type(domain.getType())
                .status(domain.getStatus())
                .description(domain.getDescription())
                .shipment(domain.getShipmentId() != null ? ShipmentJpaEntity.builder().id(domain.getShipmentId()).build() : null)
                .build();
    }

}
