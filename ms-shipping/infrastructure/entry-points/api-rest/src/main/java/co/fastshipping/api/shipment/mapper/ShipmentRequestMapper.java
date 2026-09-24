package co.fastshipping.api.shipment.mapper;

import co.fastshipping.api.shipment.request.CreateShipmentRequest;
import co.fastshipping.usecase.shipment.command.CreateShipmentCommand;

public final class ShipmentRequestMapper {
    private ShipmentRequestMapper() {
    }

    public static CreateShipmentCommand toCommand(CreateShipmentRequest request) {
        return request == null ? null : new CreateShipmentCommand(request.senderAddressId());
    }
}
