package co.fastshipping.api.licensecategory;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.licensecategory.mapper.LicenseCategoryRequestMapper;
import co.fastshipping.api.licensecategory.mapper.LicenseCategoryResponseMapper;
import co.fastshipping.api.licensecategory.request.RegisterLicenseCategoryRequest;
import co.fastshipping.api.licensecategory.response.LicenseCategoryResponse;
import co.fastshipping.usecase.licensecategory.DeleteLicenseCategoryUseCase;
import co.fastshipping.usecase.licensecategory.GetLicenseCategoryByCodeUseCase;
import co.fastshipping.usecase.licensecategory.GetLicenseCategoryUseCase;
import co.fastshipping.usecase.licensecategory.RegisterLicenseCategoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_LICENSE_CATEGORY, version = ApiPath.V1)
public class LicenseCategoryApiRest {

    private final RegisterLicenseCategoryUseCase registerLicenseCategoryUseCase;
    private final GetLicenseCategoryUseCase getLicenseCategoryUseCase;
    private final GetLicenseCategoryByCodeUseCase getLicenseCategoryByCodeUseCase;
    private final DeleteLicenseCategoryUseCase deleteLicenseCategoryUseCase;

    @PostMapping
    public ResponseEntity<LicenseCategoryResponse> register(
            @Valid @RequestBody RegisterLicenseCategoryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LicenseCategoryResponseMapper.toResponse(
                        registerLicenseCategoryUseCase.execute(
                                LicenseCategoryRequestMapper.toCommand(request)
                        )
                ));
    }

    @GetMapping
    public ResponseEntity<List<LicenseCategoryResponse>> getAll() {
        return ResponseEntity.ok(
                LicenseCategoryResponseMapper.toResponse(getLicenseCategoryUseCase.execute())
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<LicenseCategoryResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(
                LicenseCategoryResponseMapper.toResponse(getLicenseCategoryByCodeUseCase.execute(code))
        );
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        deleteLicenseCategoryUseCase.execute(code);
        return ResponseEntity.noContent().build();
    }
}
