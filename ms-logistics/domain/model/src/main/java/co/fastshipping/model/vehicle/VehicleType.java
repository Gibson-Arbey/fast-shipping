package co.fastshipping.model.vehicle;

import co.fastshipping.model.exception.InvalidFieldException;

public enum VehicleType {
    MOTORCYCLE,
    CAR,
    VAN,
    TRUCK;

    public static VehicleType fromString(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        for (VehicleType status : VehicleType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidFieldException(
                "Invalid vehicle type: " + value
        );
    }
}
