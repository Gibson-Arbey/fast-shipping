package co.fastshipping.api.address.mapper;

import co.fastshipping.api.address.request.RegisterAddressRequest;
import co.fastshipping.usecase.address.command.RegisterAddressCommand;

public class AddressRequestMapper {

    public static RegisterAddressCommand toRegisterAddressCommand(RegisterAddressRequest request) {
        if(request == null) {
            return null;
        }
        return new RegisterAddressCommand(
            request.street(),
            request.number(),
            request.neighborhood(),
            request.city(),
            request.state(),
            request.country(),
            request.postalCode()
        );
    }
}
