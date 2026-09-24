package co.fastshipping.usecase.shipment;

import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.exception.ShipmentNotFoundException;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetShipmentUseCase {
    private final ShipmentRepository shipmentRepository;

    public Shipment execute(Long id) {
        Shipment shipment = shipmentRepository.findById(id);
        if (shipment == null) {
            throw new ShipmentNotFoundException("Shipment not found: " + id);
        }
        return shipment;
    }
}
