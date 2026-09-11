package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.ParcelHistoryJpaEntity;
import co.fastshipping.jpa.entity.ParcelJpaEntity;
import co.fastshipping.model.parcelhistory.ParcelHistory;

public class ParcelHistoryJpaMapper {

    private ParcelHistoryJpaMapper() {
    }

    public static ParcelHistory toDomain(ParcelHistoryJpaEntity entity) {
        if (entity == null) return null;

        return ParcelHistory.restore(
                entity.getId(),
                entity.getParcel() != null ? entity.getParcel().getId() : null,
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUserId(),
                entity.getLocation(),
                entity.getObservation()
        );
    }

    public static ParcelHistoryJpaEntity toJpaEntity(ParcelHistory domain) {
        if (domain == null) return null;

        return ParcelHistoryJpaEntity.builder()
                .id(domain.getId())
                .parcel(domain.getParcelId() != null
                        ? ParcelJpaEntity.builder().id(domain.getParcelId()).build()
                        : null)
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .userId(domain.getUserId())
                .location(domain.getLocation())
                .observation(domain.getObservation())
                .build();
    }
}
