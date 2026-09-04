package co.fastshipping.model.parcel;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Parcel {

    private final Long id;
    private final BigDecimal weight;
    private final BigDecimal height;
    private final BigDecimal width;
    private final BigDecimal length;
    private final String description;
    private final ParcelType type;

    private Parcel(Long id, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ParcelType type, String description) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.type = type;
        this.description = description;
    }

    public static Parcel create(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ParcelType type, String description) {
        return new Parcel(null, weight, height, width, length, type, description);
    }

    public static  Parcel restore(Long id, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ParcelType type, String description) {
        return new Parcel(id, weight, height, width, length, type, description);
    }

}
