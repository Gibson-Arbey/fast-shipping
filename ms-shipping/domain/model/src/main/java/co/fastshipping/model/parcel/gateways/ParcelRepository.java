package co.fastshipping.model.parcel.gateways;

import co.fastshipping.model.parcel.Parcel;

public interface ParcelRepository {

    Parcel save(Parcel parcel);

}
