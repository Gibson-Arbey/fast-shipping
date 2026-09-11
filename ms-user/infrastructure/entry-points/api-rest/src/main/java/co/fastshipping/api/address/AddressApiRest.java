package co.fastshipping.api.address;

import co.fastshipping.api.address.mapper.AddressRequestMapper;
import co.fastshipping.api.address.mapper.AddressResponseMapper;
import co.fastshipping.api.address.request.RegisterAddressRequest;
import co.fastshipping.api.address.response.AddressResponse;
import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.filter.UserAuthentication;
import co.fastshipping.usecase.address.DeleteAddressUseCase;
import co.fastshipping.usecase.address.GetAddressesByCustomerUseCase;
import co.fastshipping.usecase.address.RegisterAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_ADDRESS, version = ApiPath.V1)
public class AddressApiRest {

    private final RegisterAddressUseCase registerAddressUseCase;
    private final GetAddressesByCustomerUseCase getAddressesByCustomerUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;

    @PostMapping
    public ResponseEntity<AddressResponse> registerAddress(
            @AuthenticationPrincipal UserAuthentication user,
            @RequestBody RegisterAddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            AddressResponseMapper.toAddressResponse(
                registerAddressUseCase.execute(AddressRequestMapper.toRegisterAddressCommand(request), user.userId()))
        );
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddressesByCustomer(
            @AuthenticationPrincipal UserAuthentication user) {
        return ResponseEntity.ok(
            AddressResponseMapper.toAddressResponseList(
                getAddressesByCustomerUseCase.execute(user.userId()))
        );
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @AuthenticationPrincipal UserAuthentication user,
            @PathVariable("addressId") Long addressId) {
        deleteAddressUseCase.execute(addressId, user.userId());
        return ResponseEntity.noContent().build();
    }
}
