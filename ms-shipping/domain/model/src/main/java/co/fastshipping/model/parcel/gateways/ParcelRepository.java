package co.fastshipping.model.parcel.gateways;

import co.fastshipping.model.parcel.Parcel;

import java.util.List;

public interface ParcelRepository {

    Parcel save(Parcel parcel);

    Parcel findById(Long id);

    List<Parcel> findAllByShipmentId(Long shipmentId);

    default List<Parcel> findAllByShipment(Long shipmentId) {
        return findAllByShipmentId(shipmentId);
    }

}
