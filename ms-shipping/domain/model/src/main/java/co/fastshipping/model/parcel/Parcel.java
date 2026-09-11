package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Parcel {

    private final Long id;
    private final UUID trackingNumber;
    private final Long destinationAddressId;
    private final BigDecimal weight;
    private final BigDecimal height;
    private final BigDecimal width;
    private final BigDecimal length;
    private final ClasificationTamanho clasificationTamanho;
    private final ParcelType type;
    private final ParcelStatus status;
    private final String description;
    private final Long shipmentId;

    private Parcel(Long id, UUID trackingNumber, Long destinationAddressId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, ParcelStatus status, String description, Long shipmentId) {
        if(destinationAddressId == null) {
            throw new InvalidFieldException("DestinationAddressId must not be null");
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
        if(status == null){
            throw new InvalidFieldException("Status must not be null");
        }

        this.id = id;
        this.trackingNumber = trackingNumber;
        this.destinationAddressId = destinationAddressId;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.clasificationTamanho = clasificationTamanho;
        this.type = type;
        this.status = status;
        this.description = description;
        this.shipmentId = shipmentId;
    }

    public static Parcel create(Long destinationAddressId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, String description) {
        return new Parcel(null, UUID.randomUUID(), destinationAddressId, weight, height, width, length, clasificationTamanho, type, ParcelStatus.CREATED, description, null);
    }

    public static  Parcel restore(Long id, UUID trackingNumber, Long destinationAddressId, BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, ClasificationTamanho clasificationTamanho, ParcelType type, ParcelStatus status, String description, Long shipmentId) {
        return new Parcel(id, trackingNumber, destinationAddressId, weight, height, width, length, clasificationTamanho, type, status, description, shipmentId);
    }

}
