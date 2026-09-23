package co.fastshipping.usecase.vehicle;

import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.exception.VehicleNotFoundException;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStatusVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public void execute(Long id, String status) {

        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle not found");
        }

        Vehicle vehicle = vehicleRepository.findById(id);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found");
        }

        vehicleRepository.save(vehicle.changeStatus(VehicleStatus.fromString(status)));
    }
}
