package co.fastshipping.api.driver;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.driver.mapper.DriverRequestMapper;
import co.fastshipping.api.driver.mapper.DriverResponseMapper;
import co.fastshipping.api.driver.request.RegisterDriverRequest;
import co.fastshipping.api.driver.request.UpdateDriverStatusRequest;
import co.fastshipping.api.driver.response.DriverResponse;
import co.fastshipping.usecase.driver.GetDriverByIdUseCase;
import co.fastshipping.usecase.driver.GetDriverUseCase;
import co.fastshipping.usecase.driver.RegisterDriverUseCase;
import co.fastshipping.usecase.driver.UpdateStatusDriverUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_DRIVER, version = ApiPath.V1)
public class DriverApiRest {

    private final RegisterDriverUseCase registerDriverUseCase;
    private final GetDriverUseCase getDriverUseCase;
    private final GetDriverByIdUseCase getDriverByIdUseCase;
    private final UpdateStatusDriverUseCase updateStatusDriverUseCase;

    @PostMapping
    public ResponseEntity<DriverResponse> register(
            @Valid @RequestBody RegisterDriverRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DriverResponseMapper.toResponse(
                        registerDriverUseCase.execute(DriverRequestMapper.toCommand(request))
                ));
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String licenseCategoryCode
    ) {
        return ResponseEntity.ok(DriverResponseMapper.toResponse(
                getDriverUseCase.execute(DriverRequestMapper.toQuery(
                    userId, status, licenseNumber, licenseCategoryCode
                ))
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(DriverResponseMapper.toResponse(getDriverByIdUseCase.execute(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDriverStatusRequest request
    ) {
        updateStatusDriverUseCase.execute(id, request.status());
        return ResponseEntity.ok().build();
    }
}
