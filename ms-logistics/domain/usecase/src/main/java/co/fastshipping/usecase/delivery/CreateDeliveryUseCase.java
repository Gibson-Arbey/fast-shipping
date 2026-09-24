package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.exception.RouteNotFoundException;
import co.fastshipping.model.route.exception.RouteStopNotFoundException;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentNotFoundException;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentConflictException;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.usecase.delivery.command.CreateDeliveryCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateDeliveryUseCase {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryTrackingGateway trackingGateway;
    private final RouteAssignmentRepository assignmentRepository;
    private final RouteRepository routeRepository;

    public Delivery execute(CreateDeliveryCommand command) {
        RouteAssignment assignment = assignmentRepository.findById(command.routeAssignmentId());
        if (assignment == null) {
            throw new RouteAssignmentNotFoundException("Route assignment not found: " + command.routeAssignmentId());
        }
        if (assignment.getStatus() != RouteAssignmentStatus.PLANNED
                && assignment.getStatus() != RouteAssignmentStatus.IN_PROGRESS) {
            throw new RouteAssignmentConflictException("Deliveries cannot be created for a completed or cancelled assignment");
        }

        Route route = routeRepository.findById(assignment.getRouteId());
        if (route == null) {
            throw new RouteNotFoundException("Route not found: " + assignment.getRouteId());
        }
        boolean stopBelongsToRoute = route.getStops().stream()
                .anyMatch(stop -> stop.getId() != null && stop.getId().equals(command.routeStopId()));
        if (!stopBelongsToRoute) {
            throw new RouteStopNotFoundException("Route stop not found in assignment route: " + command.routeStopId());
        }

        Delivery delivery = Delivery.create(command.parcelId(), command.routeAssignmentId(), command.routeStopId());
        trackingGateway.notifyStatusChange(delivery, "", "Delivery assigned to route stop " + delivery.getRouteStopId());
        return deliveryRepository.save(delivery);
    }
}
