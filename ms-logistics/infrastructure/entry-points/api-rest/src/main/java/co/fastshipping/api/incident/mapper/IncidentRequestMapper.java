package co.fastshipping.api.incident.mapper;

import co.fastshipping.api.incident.request.CreateIncidentRequest;
import co.fastshipping.usecase.incident.command.CreateIncidentCommand;
import co.fastshipping.usecase.incident.query.GetIncidentQuery;

public final class IncidentRequestMapper {
    private IncidentRequestMapper() { }

    public static CreateIncidentCommand toCommand(CreateIncidentRequest request, Long reportedBy) {
        if (request == null) return null;
        return new CreateIncidentCommand(
                request.routeAssignmentId(), request.deliveryId(), request.type(), request.description(), reportedBy
        );
    }

    public static GetIncidentQuery toQuery(Long routeAssignmentId, Long deliveryId, String type, String status) {
        return new GetIncidentQuery(routeAssignmentId, deliveryId, type, status);
    }
}
