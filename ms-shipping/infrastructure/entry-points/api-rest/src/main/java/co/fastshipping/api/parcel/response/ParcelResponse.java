package co.fastshipping.api.parcel.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ParcelResponse(
        Long id,
        UUID trackingNumber,
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        String clasificationTamanho,
        String type,
        String description
) {
}
