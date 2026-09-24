package co.fastshipping.api.shipment.response;

import java.time.LocalDateTime;

public record ShipmentResponse(
        Long id,
        Long senderAddressId,
        LocalDateTime createdAt,
        String status
) {
}
