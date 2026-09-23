package co.fastshipping.model.incident;

import co.fastshipping.model.exception.InvalidFieldException;

public enum IncidentType {
    VEHICLE_BREAKDOWN,
    ACCIDENT,
    TRAFFIC_DELAY,
    PACKAGE_DAMAGED,
    ADDRESS_NOT_FOUND,
    DELIVERY_ATTEMPT_FAILED,
    OTHER;

    public static IncidentType fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (IncidentType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new InvalidFieldException("Invalid incident type: " + value);
    }
}
