package co.fastshipping.api.routeassignment.mapper;

import co.fastshipping.api.routeassignment.response.RouteAssignmentResponse;
import co.fastshipping.model.routeassignment.RouteAssignment;

import java.util.List;

public final class RouteAssignmentResponseMapper {
    private RouteAssignmentResponseMapper() { }

    public static RouteAssignmentResponse toResponse(RouteAssignment assignment) {
        if (assignment == null) return null;
        return new RouteAssignmentResponse(
                assignment.getId(), assignment.getRouteId(), assignment.getDriverId(), assignment.getVehicleId(),
                assignment.getStatus().name(), assignment.getStartedAt(), assignment.getCompletedAt()
        );
    }

    public static List<RouteAssignmentResponse> toResponse(List<RouteAssignment> assignments) {
        return assignments.stream().map(RouteAssignmentResponseMapper::toResponse).toList();
    }
}
