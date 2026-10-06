package co.fastshipping.usecase.address;

import co.fastshipping.model.address.Address;
import co.fastshipping.model.address.exception.AddressNotFoundException;
import co.fastshipping.model.address.gateways.AddressRepository;
import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.exception.UserNotExistsException;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetNotificationEmailByAddressUseCase {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public String execute(Long addressId) {
        if (addressId == null || addressId <= 0) {
            throw new InvalidFieldException("addressId must be greater than zero");
        }
        Address address = addressRepository.findById(addressId);
        if (address == null || Boolean.TRUE.equals(address.getDeleted())) {
            throw new AddressNotFoundException("Address not found: " + addressId);
        }

        User owner = userRepository.findById(address.getCustomerId());
        if (owner == null) {
            throw new UserNotExistsException("Address owner not found: " + address.getCustomerId());
        }
        return owner.getEmail().value();
    }
}
