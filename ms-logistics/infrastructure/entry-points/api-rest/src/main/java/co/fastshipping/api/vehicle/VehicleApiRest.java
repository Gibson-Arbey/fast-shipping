package co.fastshipping.api.vehicle;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.vehicle.mapper.VehicleRequestMapper;
import co.fastshipping.api.vehicle.mapper.VehicleResponseMapper;
import co.fastshipping.api.vehicle.request.RegisterVehicleRequest;
import co.fastshipping.api.vehicle.request.UpdateVehicleStatusRequest;
import co.fastshipping.api.vehicle.response.VehicleResponse;
import co.fastshipping.usecase.vehicle.GetVehicleUseCase;
import co.fastshipping.usecase.vehicle.RegisterVehicleUseCase;
import co.fastshipping.usecase.vehicle.UpdateStatusVehicleUseCase;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_VEHICLE, version = ApiPath.V1)
public class VehicleApiRest {

    private final RegisterVehicleUseCase registerVehicleUseCase;
    private final GetVehicleUseCase getVehicleUseCase;
    private final UpdateStatusVehicleUseCase updateStatusVehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponse> registerVehicle(@Valid @RequestBody RegisterVehicleRequest request
    ) {
        return ResponseEntity.
            status(HttpStatus.CREATED)
            .body(VehicleResponseMapper.toResponse(
                registerVehicleUseCase.execute(VehicleRequestMapper.toCommand(request))
        ));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) BigDecimal minWeightCapacity,
            @RequestParam(required = false) BigDecimal maxWeightCapacity,
            @RequestParam(required = false) BigDecimal minVolumeCapacity,
            @RequestParam(required = false) BigDecimal maxVolumeCapacity,
            @RequestParam(required = false) String plate
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(VehicleResponseMapper.toResponse(
                getVehicleUseCase.execute(
                    VehicleRequestMapper.toQuery(
                        status,
                        type,
                        minWeightCapacity,
                        maxWeightCapacity,
                        minVolumeCapacity,
                        maxVolumeCapacity,
                        plate
                    )
                )
            )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleStatusRequest request
    ) {
        updateStatusVehicleUseCase.execute(id, request.status());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
