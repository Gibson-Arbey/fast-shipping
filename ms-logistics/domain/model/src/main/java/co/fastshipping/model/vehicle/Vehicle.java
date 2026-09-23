package co.fastshipping.model.vehicle;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Vehicle {

    private final Long id;

    private final VehicleStatus status;

    private final VehicleType type;

    private final BigDecimal maxWeight;

    private final BigDecimal maxVolume;

    private final String plate;

    private Vehicle(Long id, VehicleStatus status, VehicleType type, BigDecimal maxWeight, BigDecimal maxVolume, String plate) {

        if(status == null) throw new InvalidFieldException("status cannot be null");
        if(type == null) throw new InvalidFieldException("type cannot be null");
        if(maxWeight == null || maxWeight.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidFieldException("maxWeight not valid");
        if(maxVolume == null || maxVolume.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidFieldException("maxVolume not valid");
        if(plate == null) throw new InvalidFieldException("plate cannot be null");

        this.id = id;
        this.status = status;
        this.type = type;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.plate = plate;
    }

    public static Vehicle create(VehicleType type, BigDecimal maxWeight, BigDecimal maxVolume, String plate) {
        return new Vehicle(null, VehicleStatus.AVAILABLE, type, maxWeight, maxVolume, plate);
    }

    public static Vehicle restore(Long id, VehicleStatus status, VehicleType type, BigDecimal maxWeight, BigDecimal maxVolume, String plate) {
        return new Vehicle(id, status, type, maxWeight, maxVolume, plate);
    }

    public Vehicle assign() {
        requireStatus(VehicleStatus.AVAILABLE, "Vehicle can only be assigned when it is available");
        return withStatus(VehicleStatus.ASSIGNED);
    }

    public Vehicle startTransit() {
        requireStatus(VehicleStatus.ASSIGNED, "Vehicle can only start transit after being assigned");
        return withStatus(VehicleStatus.IN_TRANSIT);
    }

    public Vehicle release() {
        if (status != VehicleStatus.ASSIGNED && status != VehicleStatus.IN_TRANSIT) {
            throw new InvalidFieldException("Vehicle can only be released when assigned or in transit");
        }
        return withStatus(VehicleStatus.AVAILABLE);
    }

    public Vehicle changeStatus(VehicleStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        return withStatus(newStatus);
    }

    private Vehicle withStatus(VehicleStatus newStatus) {
        return new Vehicle(id, newStatus, type, maxWeight, maxVolume, plate);
    }

    private void requireStatus(VehicleStatus expected, String message) {
        if (status != expected) {
            throw new InvalidFieldException(message);
        }
    }
}
