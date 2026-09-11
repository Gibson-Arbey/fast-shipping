package co.fastshipping.model.parcelhistory;

import co.fastshipping.model.parcel.ParcelStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ParcelHistory {

    private final Long id;

    private final Long parcelId;

    private final ParcelStatus status;

    private final LocalDateTime createdAt;

    private final Long userId;

    private final String location;

    private final String observation;

    private ParcelHistory(Long id, Long parcelId, ParcelStatus status, LocalDateTime createdAt, Long userId, String location, String observation) {
        this.id = id;
        this.parcelId = parcelId;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.location = location;
        this.observation = observation;
    }

    public static ParcelHistory create(Long parcelId, ParcelStatus status, Long userId, String location, String observation) {
        return new ParcelHistory(null, parcelId, status, LocalDateTime.now(), userId, location, observation);
    }

    public static ParcelHistory restore(Long id, Long parcelId, ParcelStatus status, LocalDateTime createdAt, Long userId, String location, String observation) {
        return new ParcelHistory(id, parcelId, status, createdAt, userId, location, observation);
    }
}
