package co.fastshipping.usecase.parcel.command;

import java.math.BigDecimal;

public record CreateParcelCommand(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length, String type, String description) {
}
