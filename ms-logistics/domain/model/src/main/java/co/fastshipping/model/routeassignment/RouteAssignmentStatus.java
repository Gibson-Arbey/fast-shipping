package co.fastshipping.model.routeassignment;

import co.fastshipping.model.exception.InvalidFieldException;

public enum RouteAssignmentStatus {
    PLANNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public static RouteAssignmentStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (RouteAssignmentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InvalidFieldException("Invalid route assignment status: " + value);
    }
}
