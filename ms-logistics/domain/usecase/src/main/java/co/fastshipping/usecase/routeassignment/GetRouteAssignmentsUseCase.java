package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.usecase.routeassignment.query.GetRouteAssignmentQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRouteAssignmentsUseCase {
    private final RouteAssignmentRepository repository;

    public List<RouteAssignment> execute(GetRouteAssignmentQuery query) {
        return repository.findAllByFilters(query.routeId(), query.driverId(), query.vehicleId(), query.status());
    }
}
