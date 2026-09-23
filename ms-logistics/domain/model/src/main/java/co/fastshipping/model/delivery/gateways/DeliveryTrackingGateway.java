package co.fastshipping.model.delivery.gateways;

import co.fastshipping.model.delivery.Delivery;

public interface DeliveryTrackingGateway {
    void notifyStatusChange(Delivery delivery);
}
