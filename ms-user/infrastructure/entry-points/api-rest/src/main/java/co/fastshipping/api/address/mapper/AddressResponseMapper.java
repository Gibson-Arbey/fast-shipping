package co.fastshipping.api.address.mapper;

import co.fastshipping.api.address.response.AddressResponse;
import co.fastshipping.model.address.Address;

import java.util.Collections;
import java.util.List;

public class AddressResponseMapper {

    public static AddressResponse toAddressResponse(Address address) {
        if(address == null) {
            return null;
        }
        return new AddressResponse(
            address.getId(),
            address.getCustomerId(),
            address.getStreet(),
            address.getNumber(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState(),
            address.getCountry(),
            address.getPostalCode()
        );
    }

    public static List<AddressResponse> toAddressResponseList(List<Address> addresses) {
        if(addresses == null) {
            return Collections.emptyList();
        }
        return addresses.stream()
                .map(AddressResponseMapper::toAddressResponse)
                .toList();
    }
}
