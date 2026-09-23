package co.fastshipping.api.delivery.mapper;

import co.fastshipping.api.delivery.request.CreateDeliveryRequest;
import co.fastshipping.usecase.delivery.command.CreateDeliveryCommand;
import co.fastshipping.usecase.delivery.query.GetDeliveryQuery;

public final class DeliveryRequestMapper {
    private DeliveryRequestMapper() { }

    public static CreateDeliveryCommand toCommand(CreateDeliveryRequest request) {
        if (request == null) return null;
        return new CreateDeliveryCommand(request.parcelId(), request.routeAssignmentId(), request.routeStopId());
    }

    public static GetDeliveryQuery toQuery(Long parcelId, Long routeAssignmentId, Long routeStopId, String status) {
        return new GetDeliveryQuery(parcelId, routeAssignmentId, routeStopId, status);
    }
}
