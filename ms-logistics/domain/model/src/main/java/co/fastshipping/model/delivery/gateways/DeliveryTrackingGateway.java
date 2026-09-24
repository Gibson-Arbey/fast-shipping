package co.fastshipping.model.delivery.gateways;

import co.fastshipping.model.delivery.Delivery;

public interface DeliveryTrackingGateway {
    void notifyStatusChange(Delivery delivery, String location, String observation);

    default void notifyStatusChange(Delivery delivery) {
        notifyStatusChange(delivery, "", null);
    }
}
