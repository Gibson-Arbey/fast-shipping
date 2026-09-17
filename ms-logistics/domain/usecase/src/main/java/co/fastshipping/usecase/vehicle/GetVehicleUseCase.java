package co.fastshipping.usecase.vehicle;

import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import co.fastshipping.usecase.vehicle.query.GetVehicleQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> execute(GetVehicleQuery query) {
        return vehicleRepository.findAllByFilters(
                query.status(), query.type(), query.minWeightCapacity(), query.maxWeightCapacity(), query.minVolumeCapacity(), query.maxVolumeCapacity(), query.plate()
        );
    }
}
