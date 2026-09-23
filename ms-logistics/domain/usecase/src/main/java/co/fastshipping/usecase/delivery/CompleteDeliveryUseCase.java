package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.exception.DeliveryNotFoundException;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompleteDeliveryUseCase {
    private final DeliveryRepository repository;
    private final DeliveryTrackingGateway trackingGateway;

    public Delivery execute(Long id) {
        Delivery delivery = get(id).complete();
        Delivery saved = repository.save(delivery);
        trackingGateway.notifyStatusChange(saved);
        return saved;
    }

    private Delivery get(Long id) {
        Delivery delivery = repository.findById(id);
        if (delivery == null) {
            throw new DeliveryNotFoundException("Delivery not found: " + id);
        }
        return delivery;
    }
}
