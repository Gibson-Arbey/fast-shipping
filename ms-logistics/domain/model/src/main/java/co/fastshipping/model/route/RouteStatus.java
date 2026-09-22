package co.fastshipping.model.route;

import co.fastshipping.model.exception.InvalidFieldException;

public enum RouteStatus {
    ACTIVE,
    INACTIVE;

    public static RouteStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (RouteStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidFieldException("Invalid route status: " + value);
    }
}
