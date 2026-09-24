package co.fastshipping.model.parcel.gateways;

import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.shipment.Shipment;

/** Persists parcel lifecycle changes together with their history and derived shipment status. */
public interface ParcelLifecycleRepository {
    Parcel saveWithHistory(Parcel parcel, Long userId, String location, String observation, Shipment shipmentToUpdate);
}
