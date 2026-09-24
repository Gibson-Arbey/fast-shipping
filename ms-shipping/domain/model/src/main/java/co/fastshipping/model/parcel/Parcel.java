package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Set;
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
        if (id != null && id <= 0) {
            throw new InvalidFieldException("id must be greater than zero");
        }
        if(destinationAddressId == null || destinationAddressId <= 0) {
            throw new InvalidFieldException("DestinationAddressId must be greater than zero");
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
        if (trackingNumber == null) {
            throw new InvalidFieldException("TrackingNumber must not be null");
        }
        if (shipmentId != null && shipmentId <= 0) {
            throw new InvalidFieldException("shipmentId must be greater than zero");
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

    public Parcel assignToDelivery() {
        requireStatus(EnumSet.of(ParcelStatus.CREATED, ParcelStatus.CONFIRMED), "Only created or confirmed parcels can be assigned");
        return withStatus(ParcelStatus.ASSIGNED);
    }

    public Parcel markInTransit() {
        requireStatus(EnumSet.of(ParcelStatus.ASSIGNED, ParcelStatus.PICKED_UP), "Only assigned or picked-up parcels can enter transit");
        return withStatus(ParcelStatus.IN_TRANSIT);
    }

    public Parcel markDelivered() {
        requireStatus(EnumSet.of(ParcelStatus.IN_TRANSIT, ParcelStatus.OUT_FOR_DELIVERY), "Only parcels in transit or out for delivery can be delivered");
        return withStatus(ParcelStatus.DELIVERED);
    }

    public Parcel markDeliveryFailed() {
        requireStatus(EnumSet.of(ParcelStatus.IN_TRANSIT, ParcelStatus.OUT_FOR_DELIVERY), "Only parcels in transit or out for delivery can fail delivery");
        return withStatus(ParcelStatus.DELIVERY_FAILED);
    }

    public Parcel markCancelled() {
        requireStatus(EnumSet.of(ParcelStatus.CREATED, ParcelStatus.CONFIRMED, ParcelStatus.ASSIGNED), "Only uncompleted parcels can be cancelled");
        return withStatus(ParcelStatus.CANCELLED);
    }

    public Parcel associateToShipment(Long shipmentId) {
        if (shipmentId == null || shipmentId <= 0) {
            throw new InvalidFieldException("shipmentId must be greater than zero");
        }
        if (status != ParcelStatus.CREATED) {
            throw new InvalidFieldException("Only created parcels can be associated to a shipment");
        }
        if (this.shipmentId != null && !this.shipmentId.equals(shipmentId)) {
            throw new InvalidFieldException("Parcel is already associated to another shipment");
        }
        if (this.shipmentId != null) {
            return this;
        }
        return new Parcel(id, trackingNumber, destinationAddressId, weight, height, width, length,
                clasificationTamanho, type, status, description, shipmentId);
    }

    private Parcel withStatus(ParcelStatus newStatus) {
        return new Parcel(id, trackingNumber, destinationAddressId, weight, height, width, length,
                clasificationTamanho, type, newStatus, description, shipmentId);
    }

    private void requireStatus(Set<ParcelStatus> expected, String message) {
        if (!expected.contains(status)) {
            throw new InvalidFieldException(message + "; current status is " + status);
        }
    }

}
