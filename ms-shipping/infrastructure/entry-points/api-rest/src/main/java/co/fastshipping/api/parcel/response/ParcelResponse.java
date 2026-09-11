package co.fastshipping.api.parcel.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ParcelResponse(
        Long id,
        UUID trackingNumber,
        Long destinationAddressId,
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        String clasificationTamanho,
        String type,
        String status,
        String description,
        Long shipmentId
) {
}
