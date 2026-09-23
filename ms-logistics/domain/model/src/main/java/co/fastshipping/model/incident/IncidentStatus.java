package co.fastshipping.model.incident;

import co.fastshipping.model.exception.InvalidFieldException;

public enum IncidentStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CANCELLED;

    public static IncidentStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (IncidentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InvalidFieldException("Invalid incident status: " + value);
    }
}
