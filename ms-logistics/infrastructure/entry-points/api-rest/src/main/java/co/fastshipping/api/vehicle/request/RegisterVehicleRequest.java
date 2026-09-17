package co.fastshipping.api.vehicle.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegisterVehicleRequest(
        @NotBlank(message = "type is required")
        String type,
        @NotNull(message = "maxWeigth is required")
        @Positive(message = "maxWeigth must be greater than zero")
        BigDecimal maxWeigth,
        @NotNull(message = "maxVolume is required")
        @Positive(message = "maxVolume must be greater than zero")
        BigDecimal maxVolume,
        @NotBlank(message = "plate is required")
        @Size(max = 20, message = "plate must not exceed 20 characters")
        String plate
) {
}
