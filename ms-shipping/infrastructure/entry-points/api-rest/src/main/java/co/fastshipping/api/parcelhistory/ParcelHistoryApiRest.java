package co.fastshipping.api.parcelhistory;

import co.fastshipping.api.config.ApiPath;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.usecase.parcelhistory.GetParcelHistoryUseCase;
import co.fastshipping.usecase.parcelhistory.query.GetParcelHistoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_PARCEL_HISTORY, version = ApiPath.V1)
public class ParcelHistoryApiRest {

    private final GetParcelHistoryUseCase getParcelHistoryUseCase;

    @GetMapping("/{parcelId}")
    public ResponseEntity<List<ParcelHistory>> getParcelHistory(
            @PathVariable("parcelId") Long parcelId,
            @RequestParam(required = false)  LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            @RequestParam(required = false) String status) {

        GetParcelHistoryQuery query = new GetParcelHistoryQuery(
                parcelId,
                fromDate != null ? fromDate : LocalDateTime.of(1990, 1, 1, 0, 0),
                toDate != null ? toDate : LocalDateTime.now(),
                status != null,
                status != null ? ParcelStatus.valueOf(status.toUpperCase()) : null
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getParcelHistoryUseCase.execute(query));
    }
}
