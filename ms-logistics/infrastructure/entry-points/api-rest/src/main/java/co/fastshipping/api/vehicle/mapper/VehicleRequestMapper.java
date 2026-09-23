package co.fastshipping.api.vehicle.mapper;

import co.fastshipping.api.vehicle.request.RegisterVehicleRequest;
import co.fastshipping.usecase.vehicle.command.RegisterVehicleCommand;
import co.fastshipping.usecase.vehicle.query.GetVehicleQuery;

import java.math.BigDecimal;

public final class VehicleRequestMapper {

    private VehicleRequestMapper() {
    }

    public static RegisterVehicleCommand toCommand(RegisterVehicleRequest request) {
        if (request == null) return null;

        return new RegisterVehicleCommand(
                request.type(),
                request.maxWeight(),
                request.maxVolume(),
                request.plate()
        );
    }

    public static GetVehicleQuery toQuery(
            String status,
            String type,
            BigDecimal minWeightCapacity,
            BigDecimal maxWeightCapacity,
            BigDecimal minVolumeCapacity,
            BigDecimal maxVolumeCapacity,
            String plate
    ) {
        return new GetVehicleQuery(
                status,
                type,
                minWeightCapacity,
                maxWeightCapacity,
                minVolumeCapacity,
                maxVolumeCapacity,
                plate
        );
    }
}
