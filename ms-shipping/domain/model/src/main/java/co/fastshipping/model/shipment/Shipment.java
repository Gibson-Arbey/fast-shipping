package co.fastshipping.model.shipment;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Shipment {

    private final Long id;
    private final Long senderAddressId;
    private final LocalDateTime createdAt;
    private final ShipmentStatus status;

    private Shipment(Long id, Long senderAddressId, LocalDateTime createdAt, ShipmentStatus status) {
        if (id != null && id <= 0) {
            throw new InvalidFieldException("id must be greater than zero");
        }
        if (senderAddressId == null || senderAddressId <= 0) {
            throw new InvalidFieldException("senderAddressId must be greater than zero");
        }
        if (createdAt == null) {
            throw new InvalidFieldException("createdAt cannot be null");
        }
        if (status == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        this.id = id;
        this.senderAddressId = senderAddressId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static Shipment create(Long senderAddressId) {
        return new Shipment(null, senderAddressId, LocalDateTime.now(), ShipmentStatus.CREATED);
    }

    public static Shipment restore(Long id, Long senderAddressId, LocalDateTime createdAt) {
        return restore(id, senderAddressId, createdAt, ShipmentStatus.CREATED);
    }

    public static Shipment restore(Long id, Long senderAddressId, LocalDateTime createdAt, ShipmentStatus status) {
        return new Shipment(id, senderAddressId, createdAt, status);
    }

    public Shipment withStatus(ShipmentStatus newStatus) {
        return new Shipment(id, senderAddressId, createdAt, newStatus);
    }
}
