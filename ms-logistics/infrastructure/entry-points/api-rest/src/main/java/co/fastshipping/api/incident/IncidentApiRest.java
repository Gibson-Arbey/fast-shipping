package co.fastshipping.api.incident;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.api.filter.UserAuthentication;
import co.fastshipping.api.incident.mapper.IncidentRequestMapper;
import co.fastshipping.api.incident.mapper.IncidentResponseMapper;
import co.fastshipping.api.incident.request.CreateIncidentRequest;
import co.fastshipping.api.incident.response.IncidentResponse;
import co.fastshipping.usecase.incident.CancelIncidentUseCase;
import co.fastshipping.usecase.incident.CreateIncidentUseCase;
import co.fastshipping.usecase.incident.GetIncidentUseCase;
import co.fastshipping.usecase.incident.GetIncidentsUseCase;
import co.fastshipping.usecase.incident.ResolveIncidentUseCase;
import co.fastshipping.usecase.incident.StartIncidentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping(path = ApiPath.INCIDENT, version = ApiPath.V1)
public class IncidentApiRest {
    private final CreateIncidentUseCase createUseCase;
    private final GetIncidentUseCase getUseCase;
    private final GetIncidentsUseCase getAllUseCase;
    private final StartIncidentUseCase startUseCase;
    private final ResolveIncidentUseCase resolveUseCase;
    private final CancelIncidentUseCase cancelUseCase;

    @PostMapping
    public ResponseEntity<IncidentResponse> create(
            @AuthenticationPrincipal UserAuthentication user,
            @Valid @RequestBody CreateIncidentRequest request
    ) {
        Long reportedBy = user == null ? null : user.userId();
        return ResponseEntity.status(HttpStatus.CREATED).body(IncidentResponseMapper.toResponse(
                createUseCase.execute(IncidentRequestMapper.toCommand(request, reportedBy))
        ));
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAll(
            @RequestParam(required = false) Long routeAssignmentId,
            @RequestParam(required = false) Long deliveryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(IncidentResponseMapper.toResponse(getAllUseCase.execute(
                IncidentRequestMapper.toQuery(routeAssignmentId, deliveryId, type, status)
        )));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(IncidentResponseMapper.toResponse(getUseCase.execute(id)));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<IncidentResponse> start(@PathVariable Long id) {
        return ResponseEntity.ok(IncidentResponseMapper.toResponse(startUseCase.execute(id)));
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<IncidentResponse> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(IncidentResponseMapper.toResponse(resolveUseCase.execute(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<IncidentResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(IncidentResponseMapper.toResponse(cancelUseCase.execute(id)));
    }
}
