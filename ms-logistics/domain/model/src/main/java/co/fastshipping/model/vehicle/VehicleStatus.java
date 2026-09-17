package co.fastshipping.model.vehicle;

import co.fastshipping.model.exception.InvalidFieldException;

public enum VehicleStatus {
    AVAILABLE,
    ASSIGNED,
    IN_TRANSIT,
    MAINTENANCE,
    OUT_OF_SERVICE;

    public static VehicleStatus fromString(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        for (VehicleStatus status : VehicleStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidFieldException(
                "Invalid vehicle status: " + value
        );
    }
}
