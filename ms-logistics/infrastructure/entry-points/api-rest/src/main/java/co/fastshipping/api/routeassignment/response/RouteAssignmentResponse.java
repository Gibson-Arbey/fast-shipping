package co.fastshipping.api.routeassignment.response;

import java.time.LocalDateTime;

public record RouteAssignmentResponse(
        Long id,
        Long routeId,
        Long driverId,
        Long vehicleId,
        String status,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
}
