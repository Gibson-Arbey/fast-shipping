package co.fastshipping.model.parcelhistory.gateways;

import co.fastshipping.model.parcelhistory.ParcelHistory;

public interface ParcelHistoryRepository {

    ParcelHistory save(ParcelHistory parcelHistory);
}
