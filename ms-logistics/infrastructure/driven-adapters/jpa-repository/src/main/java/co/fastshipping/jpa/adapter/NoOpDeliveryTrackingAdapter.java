package co.fastshipping.jpa.adapter;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import org.springframework.stereotype.Repository;

@Repository
public class NoOpDeliveryTrackingAdapter implements DeliveryTrackingGateway {
    @Override
    public void notifyStatusChange(Delivery delivery) {
        // The inter-service tracking contract is intentionally not available yet.
    }
}
