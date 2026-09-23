package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentNotFoundException;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRouteAssignmentUseCase {
    private final RouteAssignmentRepository repository;

    public RouteAssignment execute(Long id) {
        RouteAssignment assignment = repository.findById(id);
        if (assignment == null) {
            throw new RouteAssignmentNotFoundException("Route assignment not found: " + id);
        }
        return assignment;
    }
}
