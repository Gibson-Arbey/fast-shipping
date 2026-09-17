package co.fastshipping.usecase.vehicle.query;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;

import java.math.BigDecimal;

public record GetVehicleQuery(
        VehicleStatus status,
        VehicleType type,
        BigDecimal minWeightCapacity,
        BigDecimal maxWeightCapacity,
        BigDecimal minVolumeCapacity,
        BigDecimal maxVolumeCapacity,
        String plate
) {

    public GetVehicleQuery(
            String status,
            String type,
            BigDecimal minWeightCapacity,
            BigDecimal maxWeightCapacity,
            BigDecimal minVolumeCapacity,
            BigDecimal maxVolumeCapacity,
            String plate
    ) {
        this(
                VehicleStatus.fromString(status),
                VehicleType.fromString(type),
                minWeightCapacity,
                maxWeightCapacity,
                minVolumeCapacity,
                maxVolumeCapacity,
                plate
        );

        validateRanges(minWeightCapacity, maxWeightCapacity);
        validateRanges(minVolumeCapacity, maxVolumeCapacity);
    }

    private static void validateRanges(
            BigDecimal min,
            BigDecimal max
    ) {
        if (min != null && min.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidFieldException(
                    "Minimum value cannot be negative"
            );
        }

        if (max != null && max.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFieldException(
                    "Maximum value must be greater than zero"
            );
        }

        if (min != null && max != null && min.compareTo(max) > 0) {
            throw new InvalidFieldException(
                    "Minimum value cannot be greater than maximum value"
            );
        }
    }
}
