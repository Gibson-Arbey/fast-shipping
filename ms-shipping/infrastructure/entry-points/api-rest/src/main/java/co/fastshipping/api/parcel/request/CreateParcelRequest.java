package co.fastshipping.api.parcel.request;

import java.math.BigDecimal;

public record CreateParcelRequest(
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        String type,
        String description
) {
}
