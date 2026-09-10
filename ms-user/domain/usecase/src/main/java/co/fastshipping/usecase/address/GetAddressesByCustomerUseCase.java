package co.fastshipping.usecase.address;

import co.fastshipping.model.address.Address;
import co.fastshipping.model.address.gateways.AddressRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAddressesByCustomerUseCase {

    private final AddressRepository addressRepository;

    public List<Address> execute(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

}
