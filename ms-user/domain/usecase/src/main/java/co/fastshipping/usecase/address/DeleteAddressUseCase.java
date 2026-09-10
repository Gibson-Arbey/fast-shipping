package co.fastshipping.usecase.address;

import co.fastshipping.model.address.exception.AddressNotFoundException;
import co.fastshipping.model.address.gateways.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAddressUseCase {

    private final AddressRepository addressRepository;

    public void execute(Long addressId, Long customerId) {
        if (!addressRepository.existsByIdAndCustomerId(addressId, customerId)) {
            throw new AddressNotFoundException("Address not found.");
        }
        addressRepository.deleteById(addressId);
    }
}
