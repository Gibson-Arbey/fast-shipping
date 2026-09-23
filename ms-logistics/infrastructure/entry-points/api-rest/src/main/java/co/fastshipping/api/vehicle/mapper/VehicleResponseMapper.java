package co.fastshipping.api.vehicle.mapper;

import co.fastshipping.api.vehicle.response.VehicleResponse;
import co.fastshipping.model.vehicle.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public final class VehicleResponseMapper {

    public static VehicleResponse toResponse(Vehicle vehicle) {
        if (vehicle == null) return null;

        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getStatus().name(),
                vehicle.getType().name(),
                vehicle.getMaxWeight(),
                vehicle.getMaxVolume(),
                vehicle.getPlate()
        );
    }

    public static List<VehicleResponse> toResponse(List<Vehicle> vehicles) {
        return vehicles.stream().map(VehicleResponseMapper::toResponse).collect(Collectors.toList());
    }
}
