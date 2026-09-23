package co.fastshipping.usecase.delivery;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import co.fastshipping.usecase.delivery.query.GetDeliveryQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetDeliveriesUseCase {
    private final DeliveryRepository repository;

    public List<Delivery> execute(GetDeliveryQuery query) {
        return repository.findAllByFilters(query.parcelId(), query.routeAssignmentId(), query.routeStopId(), query.status());
    }
}
