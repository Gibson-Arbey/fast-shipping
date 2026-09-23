package co.fastshipping.usecase.vehicle;

import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleType;
import co.fastshipping.model.vehicle.exception.VehicleAlreadyExistsException;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import co.fastshipping.usecase.vehicle.command.RegisterVehicleCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public Vehicle execute(RegisterVehicleCommand command) {

        if (vehicleRepository.existsByPlate(command.plate())) {
            throw new VehicleAlreadyExistsException("A vehicle with plate " + command.plate() + " already exist");
        }

        return vehicleRepository.save(Vehicle.create(VehicleType.fromString(command.type()), command.maxWeight(), command.maxVolume(), command.plate()));
    }
}
