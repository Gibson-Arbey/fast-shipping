package co.fastshipping.api.shipment;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.shipment.mapper.ShipmentRequestMapper;
import co.fastshipping.api.shipment.mapper.ShipmentResponseMapper;
import co.fastshipping.api.shipment.request.CreateShipmentRequest;
import co.fastshipping.api.shipment.response.ShipmentResponse;
import co.fastshipping.usecase.shipment.CreateShipmentUseCase;
import co.fastshipping.usecase.shipment.GetShipmentUseCase;
import co.fastshipping.usecase.shipment.GetShipmentsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_SHIPMENT, version = ApiPath.V1)
public class ShipmentApiRest {
    private final CreateShipmentUseCase createShipmentUseCase;
    private final GetShipmentUseCase getShipmentUseCase;
    private final GetShipmentsUseCase getShipmentsUseCase;

    @PostMapping
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody CreateShipmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ShipmentResponseMapper.toResponse(
                createShipmentUseCase.execute(ShipmentRequestMapper.toCommand(request))));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResponse>> getAll() {
        return ResponseEntity.ok(ShipmentResponseMapper.toResponse(getShipmentsUseCase.execute()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ShipmentResponseMapper.toResponse(getShipmentUseCase.execute(id)));
    }
}
