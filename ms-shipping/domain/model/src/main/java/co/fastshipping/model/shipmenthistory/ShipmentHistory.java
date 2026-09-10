package co.fastshipping.model.shipmenthistory;

import co.fastshipping.model.shipment.ShipmentStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShipmentHistory {

    private final Long id;

    private final Long shipmentId;

    private final ShipmentStatus status;

    private final LocalDateTime createdAt;

    private final Long userId;

    private final String location;

    private final String observation;

    private ShipmentHistory(Long id, Long shipmentId, ShipmentStatus status, LocalDateTime createdAt, Long userId, String location, String observation) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.location = location;
        this.observation = observation;
    }
}
