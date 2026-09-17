package co.fastshipping.api.vehicle.response;

import java.math.BigDecimal;

public record VehicleResponse(
        Long id,
        String status,
        String type,
        BigDecimal maxWeigth,
        BigDecimal maxVolume,
        String plate
) {
}
