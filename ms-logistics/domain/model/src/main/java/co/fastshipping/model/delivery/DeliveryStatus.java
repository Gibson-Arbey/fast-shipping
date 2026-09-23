package co.fastshipping.model.delivery;

import co.fastshipping.model.exception.InvalidFieldException;

public enum DeliveryStatus {
    PENDING,
    IN_TRANSIT,
    DELIVERED,
    FAILED,
    CANCELLED;

    public static DeliveryStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (DeliveryStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InvalidFieldException("Invalid delivery status: " + value);
    }
}
