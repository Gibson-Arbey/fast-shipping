package co.fastshipping.api.route;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.route.mapper.RouteRequestMapper;
import co.fastshipping.api.route.mapper.RouteResponseMapper;
import co.fastshipping.api.route.request.RegisterRouteRequest;
import co.fastshipping.api.route.request.UpdateRouteRequest;
import co.fastshipping.api.route.request.UpdateRouteStatusRequest;
import co.fastshipping.api.route.response.RouteResponse;
import co.fastshipping.usecase.route.GetRouteByIdUseCase;
import co.fastshipping.usecase.route.GetRouteUseCase;
import co.fastshipping.usecase.route.RegisterRouteUseCase;
import co.fastshipping.usecase.route.UpdateStatusRouteUseCase;
import co.fastshipping.usecase.route.UpdateRouteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_ROUTE, version = ApiPath.V1)
public class RouteApiRest {

    private final RegisterRouteUseCase registerRouteUseCase;
    private final GetRouteUseCase getRouteUseCase;
    private final GetRouteByIdUseCase getRouteByIdUseCase;
    private final UpdateStatusRouteUseCase updateStatusRouteUseCase;
    private final UpdateRouteUseCase updateRouteUseCase;

    @PostMapping
    public ResponseEntity<RouteResponse> register(
            @Valid @RequestBody RegisterRouteRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RouteResponseMapper.toResponse(
                        registerRouteUseCase.execute(RouteRequestMapper.toCommand(request))
                ));
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(
                RouteResponseMapper.toResponse(
                        getRouteUseCase.execute(RouteRequestMapper.toQuery(status, name))
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                RouteResponseMapper.toResponse(getRouteByIdUseCase.execute(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRouteRequest request
    ) {
        return ResponseEntity.ok(RouteResponseMapper.toResponse(
                updateRouteUseCase.execute(id, RouteRequestMapper.toCommand(request))
        ));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRouteStatusRequest request
    ) {
        updateStatusRouteUseCase.execute(id, request.status());
        return ResponseEntity.ok().build();
    }
}
