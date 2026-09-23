package co.fastshipping.usecase.routeassignment.query;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;

public record GetRouteAssignmentQuery(
        Long routeId,
        Long driverId,
        Long vehicleId,
        RouteAssignmentStatus status
) {
    public GetRouteAssignmentQuery(Long routeId, Long driverId, Long vehicleId, String status) {
        this(routeId, driverId, vehicleId, RouteAssignmentStatus.fromString(status));
        validatePositive(routeId, "routeId");
        validatePositive(driverId, "driverId");
        validatePositive(vehicleId, "vehicleId");
    }

    public static GetRouteAssignmentQuery all() {
        return new GetRouteAssignmentQuery(null, null, null, (RouteAssignmentStatus) null);
    }

    private static void validatePositive(Long value, String field) {
        if (value != null && value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
