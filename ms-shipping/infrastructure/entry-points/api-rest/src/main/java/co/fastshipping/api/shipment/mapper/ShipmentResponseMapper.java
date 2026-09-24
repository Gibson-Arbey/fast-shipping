package co.fastshipping.api.shipment.mapper;

import co.fastshipping.api.shipment.response.ShipmentResponse;
import co.fastshipping.model.shipment.Shipment;

import java.util.List;

public final class ShipmentResponseMapper {
    private ShipmentResponseMapper() {
    }

    public static ShipmentResponse toResponse(Shipment shipment) {
        if (shipment == null) {
            return null;
        }
        return new ShipmentResponse(
                shipment.getId(),
                shipment.getSenderAddressId(),
                shipment.getCreatedAt(),
                shipment.getStatus().name()
        );
    }

    public static List<ShipmentResponse> toResponse(List<Shipment> shipments) {
        return shipments.stream().map(ShipmentResponseMapper::toResponse).toList();
    }
}
