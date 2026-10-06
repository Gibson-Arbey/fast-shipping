package co.fastshipping.model.parcel.gateways;

import co.fastshipping.model.parcel.Parcel;

public interface ParcelStatusNotificationGateway {
    void notifyStatusChanged(Parcel parcel);
}
