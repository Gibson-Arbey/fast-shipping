package co.fastshipping.model.driver;

import co.fastshipping.model.exception.InvalidFieldException;

public enum DriverStatus {
    AVAILABLE,
    ASSIGNED,
    DRIVING,
    ON_LEAVE,
    SUSPENDED,
    INACTIVE;

    public static DriverStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (DriverStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidFieldException("Invalid driver status: " + value);
    }
}
