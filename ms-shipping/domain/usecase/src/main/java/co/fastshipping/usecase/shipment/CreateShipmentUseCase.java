package co.fastshipping.usecase.shipment;

import lombok.RequiredArgsConstructor;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import co.fastshipping.usecase.shipment.command.CreateShipmentCommand;
import co.fastshipping.model.exception.InvalidFieldException;

@RequiredArgsConstructor
public class CreateShipmentUseCase {

    private final ShipmentRepository shipmentRepository;

    public Shipment execute(CreateShipmentCommand command) {
        if (command == null) {
            throw new InvalidFieldException("command cannot be null");
        }
        return shipmentRepository.save(Shipment.create(command.senderAddressId()));
    }
}
