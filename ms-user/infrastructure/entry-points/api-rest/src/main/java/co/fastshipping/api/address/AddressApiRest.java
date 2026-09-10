package co.fastshipping.api.address;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.usecase.address.DeleteAddressUseCase;
import co.fastshipping.usecase.address.GetAddressesByCustomerUseCase;
import co.fastshipping.usecase.address.RegisterAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_ADDRESS, version = ApiPath.V1)
public class AddressApiRest {

    private final RegisterAddressUseCase registerAddressUseCase;
    private final GetAddressesByCustomerUseCase getAddressesByCustomerUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;
}
