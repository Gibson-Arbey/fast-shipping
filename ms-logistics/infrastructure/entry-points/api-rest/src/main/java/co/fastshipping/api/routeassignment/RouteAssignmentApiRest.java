package co.fastshipping.api.routeassignment;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.routeassignment.mapper.RouteAssignmentRequestMapper;
import co.fastshipping.api.routeassignment.mapper.RouteAssignmentResponseMapper;
import co.fastshipping.api.routeassignment.request.CreateRouteAssignmentRequest;
import co.fastshipping.api.routeassignment.response.RouteAssignmentResponse;
import co.fastshipping.usecase.routeassignment.CancelRouteAssignmentUseCase;
import co.fastshipping.usecase.routeassignment.CompleteRouteAssignmentUseCase;
import co.fastshipping.usecase.routeassignment.CreateRouteAssignmentUseCase;
import co.fastshipping.usecase.routeassignment.GetRouteAssignmentUseCase;
import co.fastshipping.usecase.routeassignment.GetRouteAssignmentsUseCase;
import co.fastshipping.usecase.routeassignment.StartRouteAssignmentUseCase;
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
@RequestMapping(path = ApiPath.ROUTE_ASSIGNMENT, version = ApiPath.V1)
public class RouteAssignmentApiRest {
    private final CreateRouteAssignmentUseCase createUseCase;
    private final GetRouteAssignmentUseCase getUseCase;
    private final GetRouteAssignmentsUseCase getAllUseCase;
    private final StartRouteAssignmentUseCase startUseCase;
    private final CompleteRouteAssignmentUseCase completeUseCase;
    private final CancelRouteAssignmentUseCase cancelUseCase;

    @PostMapping
    public ResponseEntity<RouteAssignmentResponse> create(@Valid @RequestBody CreateRouteAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RouteAssignmentResponseMapper.toResponse(
                createUseCase.execute(RouteAssignmentRequestMapper.toCommand(request))
        ));
    }

    @GetMapping
    public ResponseEntity<List<RouteAssignmentResponse>> getAll(
            @RequestParam(required = false) Long routeId,
            @RequestParam(required = false) Long driverId,
            @RequestParam(required = false) Long vehicleId,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(RouteAssignmentResponseMapper.toResponse(getAllUseCase.execute(
                RouteAssignmentRequestMapper.toQuery(routeId, driverId, vehicleId, status)
        )));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteAssignmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(RouteAssignmentResponseMapper.toResponse(getUseCase.execute(id)));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<RouteAssignmentResponse> start(@PathVariable Long id) {
        return ResponseEntity.ok(RouteAssignmentResponseMapper.toResponse(startUseCase.execute(id)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<RouteAssignmentResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(RouteAssignmentResponseMapper.toResponse(completeUseCase.execute(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<RouteAssignmentResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(RouteAssignmentResponseMapper.toResponse(cancelUseCase.execute(id)));
    }
}
