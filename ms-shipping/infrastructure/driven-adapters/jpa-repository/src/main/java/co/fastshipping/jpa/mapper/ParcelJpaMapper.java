package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.ParcelJpaEntity;
import co.fastshipping.model.parcel.Parcel;

public class ParcelJpaMapper {

    public static Parcel toDomain(ParcelJpaEntity entity) {
        if (entity == null) return  null;
        return Parcel.restore(
                entity.getId(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getWidth(),
                entity.getLength(),
                entity.getClasificationTamanho(),
                entity.getType(),
                entity.getDescription()
        );
    }

    public static ParcelJpaEntity toJpaEntity(Parcel domain) {
        if (domain == null) return null;
        return ParcelJpaEntity.builder()
                .id(domain.getId())
                .weight(domain.getWeight())
                .height(domain.getHeight())
                .width(domain.getWidth())
                .length(domain.getLength())
                .clasificationTamanho(domain.getClasificationTamanho())
                .type(domain.getType())
                .description(domain.getDescription())
                .build();
    }

}
