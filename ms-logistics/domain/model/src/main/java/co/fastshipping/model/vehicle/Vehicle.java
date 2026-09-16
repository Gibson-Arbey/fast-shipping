package co.fastshipping.model.vehicle;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Vehicle {

    private final Long id;

    private final VehicleStatus status;

    private final VehicleType type;

    private final BigDecimal maxWeigth;

    private final BigDecimal maxVolume;

    private final String plate;

    private Vehicle(Long id, VehicleStatus status, VehicleType type, BigDecimal maxWeigth, BigDecimal maxVolume, String plate) {

        if(status == null) throw new InvalidFieldException("status cannot be null");
        if(type == null) throw new InvalidFieldException("type cannot be null");
        if(maxWeigth == null) throw new InvalidFieldException("maxWeigth cannot be null");
        if(maxVolume == null) throw new InvalidFieldException("maxVolume cannot be null");
        if(plate == null) throw new InvalidFieldException("plate cannot be null");

        this.id = id;
        this.status = status;
        this.type = type;
        this.maxWeigth = maxWeigth;
        this.maxVolume = maxVolume;
        this.plate = plate;
    }

    public static Vehicle create(VehicleType type, BigDecimal maxWeigth, BigDecimal maxVolume, String plate) {
        return new Vehicle(null, VehicleStatus.AVAILABLE, type, maxWeigth, maxVolume, plate);
    }

    public static Vehicle restore(Long id, VehicleStatus status, VehicleType type, BigDecimal maxWeigth, BigDecimal maxVolume, String plate) {
        return  new Vehicle(id, status, type, maxWeigth, maxVolume, plate);
    }
}
