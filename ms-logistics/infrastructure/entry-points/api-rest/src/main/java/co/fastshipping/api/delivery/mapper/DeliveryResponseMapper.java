package co.fastshipping.api.delivery.mapper;

import co.fastshipping.api.delivery.response.DeliveryResponse;
import co.fastshipping.model.delivery.Delivery;

import java.util.List;

public final class DeliveryResponseMapper {
    private DeliveryResponseMapper() { }

    public static DeliveryResponse toResponse(Delivery delivery) {
        if (delivery == null) return null;
        return new DeliveryResponse(
                delivery.getId(), delivery.getParcelId(), delivery.getRouteAssignmentId(),
                delivery.getRouteStopId(), delivery.getStatus().name()
        );
    }

    public static List<DeliveryResponse> toResponse(List<Delivery> deliveries) {
        return deliveries.stream().map(DeliveryResponseMapper::toResponse).toList();
    }
}
