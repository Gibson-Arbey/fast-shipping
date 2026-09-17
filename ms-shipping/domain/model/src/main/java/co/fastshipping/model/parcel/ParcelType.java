package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;

public enum ParcelType {
    STANDARD,
    FRAGILE,
    PRIORITY;


    public static ParcelType fromString(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        for (ParcelType status : ParcelType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidFieldException(
                "Invalid parcel type: " + value
        );
    }

}
