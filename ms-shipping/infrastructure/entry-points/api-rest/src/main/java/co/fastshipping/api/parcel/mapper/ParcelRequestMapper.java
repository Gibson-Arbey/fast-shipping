package co.fastshipping.api.parcel.mapper;

import co.fastshipping.api.parcel.request.CreateParcelRequest;
import co.fastshipping.usecase.parcel.command.CreateParcelCommand;

public class ParcelRequestMapper {

    public static CreateParcelCommand toCreateParcelCommand(CreateParcelRequest request) {
        if(request == null) return null;
        return new CreateParcelCommand(request.weight(), request.height(), request.width(), request.length(), request.type(), request.description());
    }
}
