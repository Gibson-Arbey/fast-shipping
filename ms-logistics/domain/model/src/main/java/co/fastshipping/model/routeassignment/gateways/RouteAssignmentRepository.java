package co.fastshipping.model.routeassignment.gateways;

import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;

import java.util.List;

public interface RouteAssignmentRepository {
    RouteAssignment save(RouteAssignment assignment);
    RouteAssignment findById(Long id);
    List<RouteAssignment> findAllByFilters(Long routeId, Long driverId, Long vehicleId, RouteAssignmentStatus status);
}
