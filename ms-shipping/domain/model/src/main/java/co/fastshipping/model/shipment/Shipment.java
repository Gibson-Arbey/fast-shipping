package co.fastshipping.model.shipment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Shipment {

    private final Long id;

    private final ShipmentStatus status;

    private final String originAddress;

    private final String destinationAddress;

    private final LocalDateTime createdAt;

    private final LocalDateTime deliveredAt;

    private Shipment(Long id, ShipmentStatus status, String originAddress, String destinationAddress, LocalDateTime createdAt, LocalDateTime deliveredAt) {
        this.id = id;
        this.status = status;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.createdAt = createdAt;
        this.deliveredAt = deliveredAt;
    }
}
