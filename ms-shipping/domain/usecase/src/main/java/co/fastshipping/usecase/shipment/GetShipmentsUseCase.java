package co.fastshipping.usecase.shipment;

import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetShipmentsUseCase {
    private final ShipmentRepository shipmentRepository;

    public List<Shipment> execute() {
        return shipmentRepository.findAll();
    }
}
