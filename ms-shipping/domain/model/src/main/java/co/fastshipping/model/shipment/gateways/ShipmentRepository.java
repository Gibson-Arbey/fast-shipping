package co.fastshipping.model.shipment.gateways;

import co.fastshipping.model.shipment.Shipment;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);
}
