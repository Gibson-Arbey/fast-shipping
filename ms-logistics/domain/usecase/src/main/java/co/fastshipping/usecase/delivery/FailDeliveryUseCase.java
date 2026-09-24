package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.exception.DeliveryNotFoundException;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FailDeliveryUseCase {
    private final DeliveryRepository repository;
    private final DeliveryTrackingGateway trackingGateway;

    public Delivery execute(Long id) {
        return execute(id, "", null);
    }

    public Delivery execute(Long id, String location, String observation) {
        Delivery delivery = get(id).fail();
        trackingGateway.notifyStatusChange(delivery, location, observation);
        return repository.save(delivery);
    }

    private Delivery get(Long id) {
        Delivery delivery = repository.findById(id);
        if (delivery == null) {
            throw new DeliveryNotFoundException("Delivery not found: " + id);
        }
        return delivery;
    }
}
