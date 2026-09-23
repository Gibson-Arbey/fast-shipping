package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.exception.RouteNotFoundException;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentConflictException;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import co.fastshipping.model.driver.exception.DriverNotFoundException;
import co.fastshipping.model.vehicle.exception.VehicleNotFoundException;
import co.fastshipping.usecase.routeassignment.command.CreateRouteAssignmentCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateRouteAssignmentUseCase {

    private final RouteRepository routeRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteAssignmentRepository routeAssignmentRepository;

    public RouteAssignment execute(CreateRouteAssignmentCommand command) {
        Route route = routeRepository.findById(command.routeId());
        if (route == null) {
            throw new RouteNotFoundException("Route not found: " + command.routeId());
        }
        if (route.getStatus() != RouteStatus.ACTIVE) {
            throw new RouteAssignmentConflictException("Only active routes can be assigned");
        }

        Driver driver = driverRepository.findById(command.driverId());
        if (driver == null) {
            throw new DriverNotFoundException("Driver not found: " + command.driverId());
        }
        Vehicle vehicle = vehicleRepository.findById(command.vehicleId());
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found: " + command.vehicleId());
        }
        if (driver.getStatus() != co.fastshipping.model.driver.DriverStatus.AVAILABLE) {
            throw new RouteAssignmentConflictException("Driver is not available");
        }
        if (vehicle.getStatus() != co.fastshipping.model.vehicle.VehicleStatus.AVAILABLE) {
            throw new RouteAssignmentConflictException("Vehicle is not available");
        }

        RouteAssignment assignment = routeAssignmentRepository.save(
                RouteAssignment.create(command.routeId(), command.driverId(), command.vehicleId())
        );
        driverRepository.save(driver.assign());
        vehicleRepository.save(vehicle.assign());
        return assignment;
    }
}
