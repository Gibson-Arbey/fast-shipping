package co.fastshipping.api.shipment.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateShipmentRequest(
        @NotNull(message = "senderAddressId is required")
        @Positive(message = "senderAddressId must be greater than zero")
        Long senderAddressId
) {
}
