package co.fastshipping.api.delivery;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.delivery.mapper.DeliveryRequestMapper;
import co.fastshipping.api.delivery.mapper.DeliveryResponseMapper;
import co.fastshipping.api.delivery.request.CreateDeliveryRequest;
import co.fastshipping.api.delivery.response.DeliveryResponse;
import co.fastshipping.usecase.delivery.CancelDeliveryUseCase;
import co.fastshipping.usecase.delivery.CompleteDeliveryUseCase;
import co.fastshipping.usecase.delivery.CreateDeliveryUseCase;
import co.fastshipping.usecase.delivery.FailDeliveryUseCase;
import co.fastshipping.usecase.delivery.GetDeliveriesUseCase;
import co.fastshipping.usecase.delivery.GetDeliveryUseCase;
import co.fastshipping.usecase.delivery.StartDeliveryUseCase;
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
@RequestMapping(path = ApiPath.DELIVERY, version = ApiPath.V1)
public class DeliveryApiRest {
    private final CreateDeliveryUseCase createUseCase;
    private final GetDeliveryUseCase getUseCase;
    private final GetDeliveriesUseCase getAllUseCase;
    private final StartDeliveryUseCase startUseCase;
    private final CompleteDeliveryUseCase completeUseCase;
    private final FailDeliveryUseCase failUseCase;
    private final CancelDeliveryUseCase cancelUseCase;

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody CreateDeliveryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(DeliveryResponseMapper.toResponse(
                createUseCase.execute(DeliveryRequestMapper.toCommand(request))
        ));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAll(
            @RequestParam(required = false) Long parcelId,
            @RequestParam(required = false) Long routeAssignmentId,
            @RequestParam(required = false) Long routeStopId,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(getAllUseCase.execute(
                DeliveryRequestMapper.toQuery(parcelId, routeAssignmentId, routeStopId, status)
        )));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(getUseCase.execute(id)));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<DeliveryResponse> start(@PathVariable Long id) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(startUseCase.execute(id)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<DeliveryResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(completeUseCase.execute(id)));
    }

    @PatchMapping("/{id}/fail")
    public ResponseEntity<DeliveryResponse> fail(@PathVariable Long id) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(failUseCase.execute(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<DeliveryResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(DeliveryResponseMapper.toResponse(cancelUseCase.execute(id)));
    }
}
