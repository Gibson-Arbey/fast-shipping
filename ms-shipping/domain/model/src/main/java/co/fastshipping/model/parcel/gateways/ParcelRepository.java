package co.fastshipping.model.parcel.gateways;

import co.fastshipping.model.parcel.Parcel;

import java.util.List;

public interface ParcelRepository {

    Parcel save(Parcel parcel);

    List<Parcel> findAllByShipment(Long shipmentId);

}
