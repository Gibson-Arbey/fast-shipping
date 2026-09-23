package co.fastshipping.usecase.vehicle.command;

import java.math.BigDecimal;

public record RegisterVehicleCommand(String type, BigDecimal maxWeight, BigDecimal maxVolume, String plate) {
}
