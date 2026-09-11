package co.fastshipping.model.shipment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Shipment {

    private final Long id;

    private final Long senderAddressId;

    private final LocalDateTime createdAt;

    private Shipment(Long id, Long senderAddressId, LocalDateTime createdAt) {
        this.id = id;
        this.senderAddressId = senderAddressId;
        this.createdAt = createdAt;
    }

    public static Shipment create(Long senderAddressId) {
        return new Shipment(null, senderAddressId, LocalDateTime.now());
    }

    public static Shipment restore(Long id, Long senderAddressId, LocalDateTime createdAt) {
        return new Shipment(id, senderAddressId, createdAt);
    }
}
