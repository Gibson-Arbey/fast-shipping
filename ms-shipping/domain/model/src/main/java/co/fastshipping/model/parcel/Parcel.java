package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Parcel {

    private final Long id;
    private final UUID trackingNumber;
    private final Long userId;
    private final BigDecimal weight;
    private final BigDecimal height;
    private final BigDecimal width;
    private final BigDecimal length;
    private final ClasificationTamanho clasificationTamanho;
    private final ParcelType type;
    private final String description;

    private Parcel(Long id, UUID trackingNumber, Long userId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        if(userId == null) {
            throw new InvalidFieldException("UserId must not be null");
        }
        if(weight == null || weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidFieldException("Weight must be greater than zero");
        }
        if(height == null || height.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidFieldException("Height must be greater than zero");
        }
        if(width == null || width.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidFieldException("Width must be greater than zero");
        }
        if(length == null || length.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidFieldException("Length must be greater than zero");
        }
        if(clasificationTamanho == null){
            throw new InvalidFieldException("ClasificationTamanho must not be null");
        }
        if(type == null){
            throw new InvalidFieldException("Type must not be null");
        }

        this.id = id;
        this.trackingNumber = trackingNumber;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.clasificationTamanho = clasificationTamanho;
        this.type = type;
        this.description = description;
    }

    public static Parcel create(Long userId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        return new Parcel(null, null, userId, weight, height, width, length, clasificationTamanho, type, description);
    }

    public static  Parcel restore(Long id, UUID trackingNumber, Long userId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        return new Parcel(id, trackingNumber, userId, weight, height, width, length, clasificationTamanho, type, description);
    }

}
