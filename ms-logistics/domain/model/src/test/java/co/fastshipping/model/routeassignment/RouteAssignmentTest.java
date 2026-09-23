package co.fastshipping.model.routeassignment;

import co.fastshipping.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteAssignmentTest {
    @Test
    void newAssignmentStartsPlannedWithoutDates() {
        RouteAssignment assignment = RouteAssignment.create(1L, 2L, 3L);

        assertEquals(RouteAssignmentStatus.PLANNED, assignment.getStatus());
        assertNull(assignment.getStartedAt());
        assertNull(assignment.getCompletedAt());
    }

    @Test
    void assignmentFollowsExecutionLifecycle() {
        LocalDateTime startedAt = LocalDateTime.of(2026, 1, 1, 8, 0);
        LocalDateTime completedAt = startedAt.plusHours(2);

        RouteAssignment assignment = RouteAssignment.create(1L, 2L, 3L)
                .start(startedAt)
                .complete(completedAt);

        assertEquals(RouteAssignmentStatus.COMPLETED, assignment.getStatus());
        assertEquals(startedAt, assignment.getStartedAt());
        assertEquals(completedAt, assignment.getCompletedAt());
    }

    @Test
    void completedAssignmentCannotReturnToProgress() {
        RouteAssignment assignment = RouteAssignment.create(1L, 2L, 3L)
                .start(LocalDateTime.now())
                .complete(LocalDateTime.now().plusMinutes(1));

        assertThrows(InvalidFieldException.class, () -> assignment.start(LocalDateTime.now()));
    }

    @Test
    void identifiersAreMandatory() {
        assertThrows(InvalidFieldException.class, () -> RouteAssignment.create(null, 2L, 3L));
        assertThrows(InvalidFieldException.class, () -> RouteAssignment.create(1L, 0L, 3L));
    }
}
