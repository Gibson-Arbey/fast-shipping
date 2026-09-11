package co.fastshipping.api.parcel.mapper;

import co.fastshipping.api.parcel.response.ParcelResponse;
import co.fastshipping.model.parcel.Parcel;

public class ParcelResponseMapper {

    public static ParcelResponse toResponse(Parcel parcel) {
        if(parcel == null) return null;
        return new ParcelResponse(
            parcel.getId(),
            parcel.getTrackingNumber(),
            parcel.getDestinationAddressId(),
            parcel.getWeight(),
            parcel.getHeight(),
            parcel.getWidth(),
            parcel.getLength(),
            parcel.getClasificationTamanho().name(),
            parcel.getType().name(),
            parcel.getStatus().name(),
            parcel.getDescription(),
            parcel.getShipmentId()
        );
    }
}
