package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentNotFoundException;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelRouteAssignmentUseCase {
    private final RouteAssignmentRepository assignmentRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public RouteAssignment execute(Long id) {
        RouteAssignment current = assignmentRepository.findById(id);
        if (current == null) {
            throw new RouteAssignmentNotFoundException("Route assignment not found: " + id);
        }
        var driver = driverRepository.findById(current.getDriverId());
        var vehicle = vehicleRepository.findById(current.getVehicleId());
        if (driver == null || vehicle == null) {
            throw new RouteAssignmentNotFoundException("Assignment resources could not be found");
        }
        RouteAssignment updated = current.cancel();
        assignmentRepository.save(updated);
        driverRepository.save(driver.release());
        vehicleRepository.save(vehicle.release());
        return updated;
    }
}
