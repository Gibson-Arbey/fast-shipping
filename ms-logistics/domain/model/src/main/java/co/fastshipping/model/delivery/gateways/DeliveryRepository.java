package co.fastshipping.model.delivery.gateways;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;

import java.util.List;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Delivery findById(Long id);
    List<Delivery> findAllByFilters(Long parcelId, Long routeAssignmentId, Long routeStopId, DeliveryStatus status);
}
