package co.fastshipping.api.vehicle.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateVehicleStatusRequest(
        @NotBlank(message = "status is required")
        String status
) {
}
