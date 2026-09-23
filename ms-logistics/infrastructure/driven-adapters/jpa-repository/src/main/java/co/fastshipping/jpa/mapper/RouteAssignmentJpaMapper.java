package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.RouteAssignmentJpaEntity;
import co.fastshipping.model.routeassignment.RouteAssignment;

public final class RouteAssignmentJpaMapper {
    private RouteAssignmentJpaMapper() { }

    public static RouteAssignmentJpaEntity toEntity(RouteAssignment assignment) {
        if (assignment == null) return null;
        return RouteAssignmentJpaEntity.builder()
                .id(assignment.getId())
                .routeId(assignment.getRouteId())
                .driverId(assignment.getDriverId())
                .vehicleId(assignment.getVehicleId())
                .status(assignment.getStatus())
                .startedAt(assignment.getStartedAt())
                .completedAt(assignment.getCompletedAt())
                .build();
    }

    public static RouteAssignment toDomain(RouteAssignmentJpaEntity entity) {
        if (entity == null) return null;
        return RouteAssignment.restore(
                entity.getId(), entity.getRouteId(), entity.getDriverId(), entity.getVehicleId(),
                entity.getStatus(), entity.getStartedAt(), entity.getCompletedAt()
        );
    }
}
