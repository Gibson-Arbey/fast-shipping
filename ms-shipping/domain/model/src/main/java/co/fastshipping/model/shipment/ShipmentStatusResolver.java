package co.fastshipping.model.shipment;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.Parcel;

import java.util.List;

public class ShipmentStatusResolver {

    public ShipmentStatus resolve(Shipment shipment, List<Parcel> parcels) {
        if (shipment == null) {
            throw new InvalidFieldException("shipment cannot be null");
        }
        if (parcels == null) {
            throw new InvalidFieldException("parcels cannot be null");
        }
        if (parcels.stream().anyMatch(parcel -> parcel == null || parcel.getStatus() == null)) {
            throw new InvalidFieldException("parcels and parcel statuses cannot be null");
        }
        if (parcels.isEmpty() || parcels.stream().allMatch(parcel -> parcel.getStatus() == ParcelStatus.CREATED)) {
            return ShipmentStatus.CREATED;
        }
        if (parcels.stream().allMatch(parcel -> parcel.getStatus() == ParcelStatus.DELIVERED)) {
            return ShipmentStatus.COMPLETED;
        }
        if (parcels.stream().allMatch(parcel -> parcel.getStatus() == ParcelStatus.CANCELLED)) {
            return ShipmentStatus.CANCELLED;
        }
        return ShipmentStatus.PROCESSING;
    }
}
