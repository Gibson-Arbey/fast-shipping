package co.fastshipping.usecase.incident;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.gateways.IncidentRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.usecase.incident.command.CreateIncidentCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentUseCaseTest {
    @Mock IncidentRepository incidentRepository;
    @Mock RouteAssignmentRepository assignmentRepository;
    @Mock DeliveryRepository deliveryRepository;
    @InjectMocks CreateIncidentUseCase createUseCase;

    @Test
    void createsIncidentWithAuthenticatedReporter() {
        when(assignmentRepository.findById(1L)).thenReturn(RouteAssignment.create(1L, 2L, 3L));
        when(incidentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Incident incident = createUseCase.execute(new CreateIncidentCommand(
                1L, null, "TRAFFIC_DELAY", "Traffic delay", 99L
        ));

        assertEquals(IncidentStatus.OPEN, incident.getStatus());
        assertEquals(99L, incident.getReportedBy());
    }

    @Test
    void incidentCanReferenceDeliveryFromSameAssignment() {
        when(assignmentRepository.findById(1L)).thenReturn(RouteAssignment.create(1L, 2L, 3L));
        when(deliveryRepository.findById(4L)).thenReturn(Delivery.restore(4L, 100L, 1L, 10L, DeliveryStatus.PENDING));
        when(incidentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Incident incident = createUseCase.execute(new CreateIncidentCommand(
                1L, 4L, "OTHER", "Operational issue", 99L
        ));

        assertEquals(4L, incident.getDeliveryId());
    }
}
