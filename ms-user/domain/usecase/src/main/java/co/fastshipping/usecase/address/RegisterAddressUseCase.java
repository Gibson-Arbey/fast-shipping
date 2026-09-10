package co.fastshipping.usecase.address;

import co.fastshipping.model.address.Address;
import co.fastshipping.model.address.gateways.AddressRepository;
import co.fastshipping.usecase.address.command.RegisterAddressCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterAddressUseCase {

    private final AddressRepository addressRepository;

    public Address execute(RegisterAddressCommand command, Long customerId) {
        Address address = Address.create(
            customerId,
            command.street(),
            command.number(),
            command.neighborhood(),
            command.city(),
            command.state(),
            command.country(),
            command.postalCode()
        );
        return addressRepository.save(address);

    }
}
