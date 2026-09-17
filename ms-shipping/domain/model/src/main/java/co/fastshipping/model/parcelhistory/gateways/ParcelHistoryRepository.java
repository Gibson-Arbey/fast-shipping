package co.fastshipping.model.parcelhistory.gateways;

import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcelhistory.ParcelHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface ParcelHistoryRepository {

    ParcelHistory save(ParcelHistory parcelHistory);

    List<ParcelHistory> findAllByFilters(Long parcelId, LocalDateTime fromDate, LocalDateTime toDate, ParcelStatus status);
}
