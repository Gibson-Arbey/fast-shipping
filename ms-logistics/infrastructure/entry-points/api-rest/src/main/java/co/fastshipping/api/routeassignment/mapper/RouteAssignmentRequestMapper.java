package co.fastshipping.api.routeassignment.mapper;

import co.fastshipping.api.routeassignment.request.CreateRouteAssignmentRequest;
import co.fastshipping.usecase.routeassignment.command.CreateRouteAssignmentCommand;
import co.fastshipping.usecase.routeassignment.query.GetRouteAssignmentQuery;

public final class RouteAssignmentRequestMapper {
    private RouteAssignmentRequestMapper() { }

    public static CreateRouteAssignmentCommand toCommand(CreateRouteAssignmentRequest request) {
        if (request == null) return null;
        return new CreateRouteAssignmentCommand(request.routeId(), request.driverId(), request.vehicleId());
    }

    public static GetRouteAssignmentQuery toQuery(Long routeId, Long driverId, Long vehicleId, String status) {
        return new GetRouteAssignmentQuery(routeId, driverId, vehicleId, status);
    }
}
