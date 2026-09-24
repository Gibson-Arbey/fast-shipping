package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;

import java.math.BigDecimal;

public enum ClasificationTamanho {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE;

    public static ClasificationTamanho fromDimensions(
            BigDecimal height,
            BigDecimal width,
            BigDecimal length
    ) {

        if (height == null || height.compareTo(BigDecimal.ZERO) <= 0
                || width == null || width.compareTo(BigDecimal.ZERO) <= 0
                || length == null || length.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFieldException("Parcel dimensions must be greater than zero");
        }

        BigDecimal volume = height
                .multiply(width)
                .multiply(length);

        if (volume.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            return SMALL;
        }

        if (volume.compareTo(BigDecimal.valueOf(5000)) <= 0) {
            return MEDIUM;
        }

        if (volume.compareTo(BigDecimal.valueOf(10000)) <= 0) {
            return LARGE;
        }

        return EXTRA_LARGE;
    }
}
