package co.fastshipping.model.routeassignment;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RouteAssignment {

    private final Long id;
    private final Long routeId;
    private final Long driverId;
    private final Long vehicleId;
    private final RouteAssignmentStatus status;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;

    private RouteAssignment(
            Long id,
            Long routeId,
            Long driverId,
            Long vehicleId,
            RouteAssignmentStatus status,
            LocalDateTime startedAt,
            LocalDateTime completedAt
    ) {
        requirePositive(routeId, "routeId");
        requirePositive(driverId, "driverId");
        requirePositive(vehicleId, "vehicleId");
        if (status == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        if (startedAt != null && completedAt != null && completedAt.isBefore(startedAt)) {
            throw new InvalidFieldException("completedAt cannot be before startedAt");
        }
        if ((status == RouteAssignmentStatus.PLANNED || status == RouteAssignmentStatus.CANCELLED)
                && (startedAt != null || completedAt != null)) {
            throw new InvalidFieldException("planned or cancelled assignments cannot have execution dates");
        }
        if (status == RouteAssignmentStatus.IN_PROGRESS && (startedAt == null || completedAt != null)) {
            throw new InvalidFieldException("an in-progress assignment must have startedAt only");
        }
        if (status == RouteAssignmentStatus.COMPLETED && (startedAt == null || completedAt == null)) {
            throw new InvalidFieldException("a completed assignment must have startedAt and completedAt");
        }

        this.id = id;
        this.routeId = routeId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public static RouteAssignment create(Long routeId, Long driverId, Long vehicleId) {
        return new RouteAssignment(null, routeId, driverId, vehicleId, RouteAssignmentStatus.PLANNED, null, null);
    }

    public static RouteAssignment restore(
            Long id,
            Long routeId,
            Long driverId,
            Long vehicleId,
            RouteAssignmentStatus status,
            LocalDateTime startedAt,
            LocalDateTime completedAt
    ) {
        return new RouteAssignment(id, routeId, driverId, vehicleId, status, startedAt, completedAt);
    }

    public RouteAssignment start(LocalDateTime at) {
        if (status != RouteAssignmentStatus.PLANNED) {
            throw new InvalidFieldException("Only planned assignments can start");
        }
        if (at == null) {
            throw new InvalidFieldException("startedAt cannot be null");
        }
        return new RouteAssignment(id, routeId, driverId, vehicleId, RouteAssignmentStatus.IN_PROGRESS, at, null);
    }

    public RouteAssignment complete(LocalDateTime at) {
        if (status != RouteAssignmentStatus.IN_PROGRESS) {
            throw new InvalidFieldException("Only in-progress assignments can complete");
        }
        if (at == null || at.isBefore(startedAt)) {
            throw new InvalidFieldException("completedAt must be on or after startedAt");
        }
        return new RouteAssignment(id, routeId, driverId, vehicleId, RouteAssignmentStatus.COMPLETED, startedAt, at);
    }

    public RouteAssignment cancel() {
        if (status != RouteAssignmentStatus.PLANNED) {
            throw new InvalidFieldException("Only planned assignments can be cancelled");
        }
        return new RouteAssignment(id, routeId, driverId, vehicleId, RouteAssignmentStatus.CANCELLED, null, null);
    }

    private static void requirePositive(Long value, String field) {
        if (value == null || value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
