package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.exception.DeliveryNotFoundException;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDeliveryUseCase {
    private final DeliveryRepository repository;

    public Delivery execute(Long id) {
        Delivery delivery = repository.findById(id);
        if (delivery == null) {
            throw new DeliveryNotFoundException("Delivery not found: " + id);
        }
        return delivery;
    }
}
