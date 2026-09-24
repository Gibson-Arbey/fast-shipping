package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;

public enum ParcelStatus {
    CREATED,
    CONFIRMED,
    ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    DELIVERY_FAILED,
    CANCELLED;

    public static ParcelStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidFieldException("Parcel status is required");
        }
        for (ParcelStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InvalidFieldException("Invalid parcel status: " + value);
    }
}
