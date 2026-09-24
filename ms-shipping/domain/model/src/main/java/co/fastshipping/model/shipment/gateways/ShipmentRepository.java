package co.fastshipping.model.shipment.gateways;

import co.fastshipping.model.shipment.Shipment;

import java.util.List;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    Shipment findById(Long id);

    List<Shipment> findAll();
}
