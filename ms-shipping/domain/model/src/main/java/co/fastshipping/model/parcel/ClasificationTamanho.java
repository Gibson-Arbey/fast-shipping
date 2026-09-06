package co.fastshipping.model.parcel;

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
