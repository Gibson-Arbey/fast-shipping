package co.fastshipping.api.delivery.request;

import jakarta.validation.constraints.Size;

public record DeliveryStatusUpdateRequest(
        @Size(max = 255, message = "location cannot exceed 255 characters") String location,
        @Size(max = 500, message = "observation cannot exceed 500 characters") String observation
) {
}
