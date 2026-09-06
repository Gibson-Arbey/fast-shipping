package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ParcelJpaMapper;
import co.fastshipping.jpa.repository.ParcelJpaRepository;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ParcelJpaAdapter implements ParcelRepository {

    private final ParcelJpaRepository parcelJpaRepository;

    @Override
    @Transactional
    public Parcel save(Parcel parcel) {
        return ParcelJpaMapper.toDomain(parcelJpaRepository.save(ParcelJpaMapper.toJpaEntity(parcel)));
    }
}
