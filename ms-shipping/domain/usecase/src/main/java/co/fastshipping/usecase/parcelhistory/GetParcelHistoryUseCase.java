package co.fastshipping.usecase.parcelhistory;

import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.parcelhistory.gateways.ParcelHistoryRepository;
import co.fastshipping.usecase.parcelhistory.query.GetParcelHistoryQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetParcelHistoryUseCase {

    private final ParcelHistoryRepository parcelHistoryRepository;

    public List<ParcelHistory> execute(GetParcelHistoryQuery query) {
        return parcelHistoryRepository.findAllByFilters(
                query.parcelId(),
                query.fromDate(),
                query.toDate(),
                query.status());
    }
}
