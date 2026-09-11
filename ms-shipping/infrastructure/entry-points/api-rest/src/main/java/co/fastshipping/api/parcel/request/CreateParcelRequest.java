package co.fastshipping.api.parcel.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateParcelRequest(
        @NotNull(message = "destinationAddressId is required")
        Long destinationAddressId,
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        String type,
        String description
) {
}
