package co.fastshipping.api.parcel;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.filter.UserAuthentication;
import co.fastshipping.api.parcel.mapper.ParcelRequestMapper;
import co.fastshipping.api.parcel.mapper.ParcelResponseMapper;
import co.fastshipping.api.parcel.request.CreateParcelRequest;
import co.fastshipping.api.parcel.response.ParcelResponse;
import co.fastshipping.usecase.parcel.CreateParcelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_PARCEL,version = ApiPath.V1)
public class ParcelApiRest {

    private final CreateParcelUseCase createParcelUseCase;

    @PostMapping
    public ResponseEntity<ParcelResponse> createParcel(
            @AuthenticationPrincipal UserAuthentication user,
            @Valid @RequestBody CreateParcelRequest request
    ) {
        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ParcelResponseMapper
                    .toResponse(createParcelUseCase.execute(
                            user.userId(),
                            ParcelRequestMapper.toCreateParcelCommand(request)
                    ))
        );
    }
}
