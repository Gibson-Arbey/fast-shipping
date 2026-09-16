package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.VehicleJpaEntity;
import co.fastshipping.model.vehicle.Vehicle;

public class VehicleJpaMapper {

    public static VehicleJpaEntity toEntity(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return VehicleJpaEntity
                .builder()
                .id(vehicle.getId())
                .status(vehicle.getStatus())
                .type(vehicle.getType())
                .maxWeigth(vehicle.getMaxWeigth())
                .maxVolume(vehicle.getMaxVolume())
                .plate(vehicle.getPlate())
                .build();
    }

    public static Vehicle toDomain(VehicleJpaEntity entity) {
        if (entity == null) {
            return  null;
        }

        return Vehicle.restore(
            entity.getId(),
            entity.getStatus(),
            entity.getType(),
            entity.getMaxWeigth(),
            entity.getMaxVolume(),
            entity.getPlate()
        );
    }
}
