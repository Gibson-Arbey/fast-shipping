package co.fastshipping.usecase.incident;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.IncidentType;
import co.fastshipping.model.incident.gateways.IncidentRepository;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentNotFoundException;
import co.fastshipping.model.routeassignment.exception.RouteAssignmentConflictException;
import co.fastshipping.model.delivery.exception.DeliveryNotFoundException;
import co.fastshipping.usecase.incident.command.CreateIncidentCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateIncidentUseCase {
    private final IncidentRepository incidentRepository;
    private final RouteAssignmentRepository assignmentRepository;
    private final DeliveryRepository deliveryRepository;

    public Incident execute(CreateIncidentCommand command) {
        if (assignmentRepository.findById(command.routeAssignmentId()) == null) {
            throw new RouteAssignmentNotFoundException("Route assignment not found: " + command.routeAssignmentId());
        }
        if (command.deliveryId() != null) {
            Delivery delivery = deliveryRepository.findById(command.deliveryId());
            if (delivery == null) {
                throw new DeliveryNotFoundException("Delivery not found: " + command.deliveryId());
            }
            if (!command.routeAssignmentId().equals(delivery.getRouteAssignmentId())) {
                throw new RouteAssignmentConflictException("Delivery does not belong to route assignment");
            }
        }

        return incidentRepository.save(Incident.create(
                command.routeAssignmentId(),
                command.deliveryId(),
                IncidentType.fromString(command.type()),
                command.description(),
                command.reportedBy()
        ));
    }
}
