package co.fastshipping.usecase.parcelhistory.query;

import co.fastshipping.model.parcel.ParcelStatus;

import java.time.LocalDateTime;

public record GetParcelHistoryQuery(Long parcelId, LocalDateTime fromDate, LocalDateTime toDate, Boolean applystatus, ParcelStatus status) {
}
