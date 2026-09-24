package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.RouteStop;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.usecase.delivery.command.CreateDeliveryCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryUseCaseTest {
    @Mock DeliveryRepository deliveryRepository;
    @Mock RouteAssignmentRepository assignmentRepository;
    @Mock RouteRepository routeRepository;
    @Mock DeliveryTrackingGateway trackingGateway;
    @InjectMocks CreateDeliveryUseCase createUseCase;
    @InjectMocks StartDeliveryUseCase startUseCase;

    @Test
    void createsDeliveryOnlyForAStopInTheAssignmentRoute() {
        RouteAssignment assignment = RouteAssignment.create(1L, 2L, 3L);
        Route route = Route.restore(1L, "Route", Set.of(
                RouteStop.restore(10L, 1L, 1, "A"),
                RouteStop.restore(11L, 1L, 2, "B")
        ), RouteStatus.ACTIVE);
        when(assignmentRepository.findById(5L)).thenReturn(assignment);
        when(routeRepository.findById(1L)).thenReturn(route);
        when(deliveryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Delivery delivery = createUseCase.execute(new CreateDeliveryCommand(100L, 5L, 10L));

        assertEquals(DeliveryStatus.PENDING, delivery.getStatus());
        verify(trackingGateway).notifyStatusChange(delivery, "", "Delivery assigned to route stop 10");
    }

    @Test
    void startingDeliveryNotifiesTrackingPort() {
        Delivery pending = Delivery.restore(9L, 100L, 5L, 10L, DeliveryStatus.PENDING);
        when(deliveryRepository.findById(9L)).thenReturn(pending);
        when(deliveryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Delivery result = startUseCase.execute(9L, "Hub A", "Package picked up");

        assertEquals(DeliveryStatus.IN_TRANSIT, result.getStatus());
        verify(trackingGateway).notifyStatusChange(result, "Hub A", "Package picked up");
    }
}
