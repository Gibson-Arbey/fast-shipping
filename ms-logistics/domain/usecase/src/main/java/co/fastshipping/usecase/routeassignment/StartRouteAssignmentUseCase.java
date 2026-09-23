package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentNotFoundException;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class StartRouteAssignmentUseCase {
    private final RouteAssignmentRepository assignmentRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public RouteAssignment execute(Long id) {
        RouteAssignment current = get(id);
        var driver = driverRepository.findById(current.getDriverId());
        var vehicle = vehicleRepository.findById(current.getVehicleId());
        if (driver == null || vehicle == null) {
            throw new RouteAssignmentNotFoundException("Assignment resources could not be found");
        }
        RouteAssignment updated = current.start(LocalDateTime.now());
        assignmentRepository.save(updated);
        driverRepository.save(driver.startDriving());
        vehicleRepository.save(vehicle.startTransit());
        return updated;
    }

    private RouteAssignment get(Long id) {
        RouteAssignment assignment = assignmentRepository.findById(id);
        if (assignment == null) {
            throw new RouteAssignmentNotFoundException("Route assignment not found: " + id);
        }
        return assignment;
    }
}
