package co.fastshipping.model.parcel;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Parcel {

    private final Long id;
    private final UUID trackingNumber;
    private final BigDecimal weight;
    private final BigDecimal height;
    private final BigDecimal width;
    private final BigDecimal length;
    private final ClasificationTamanho clasificationTamanho;
    private final ParcelType type;
    private final String description;

    private Parcel(Long id, UUID trackingNumber, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.clasificationTamanho = clasificationTamanho;
        this.type = type;
        this.description = description;
    }

    public static Parcel create(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        return new Parcel(null, UUID.randomUUID(), weight, height, width, length, clasificationTamanho, type, description);
    }

    public static  Parcel restore(Long id, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        return new Parcel(id, UUID.randomUUID(), weight, height, width, length, clasificationTamanho, type, description);
    }

}
