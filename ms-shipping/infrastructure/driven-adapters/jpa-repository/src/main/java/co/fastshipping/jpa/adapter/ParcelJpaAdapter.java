package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ParcelJpaMapper;
import co.fastshipping.jpa.repository.ParcelJpaRepository;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParcelJpaAdapter implements ParcelRepository {

    private final ParcelJpaRepository parcelJpaRepository;

    @Override
    @Transactional
    public Parcel save(Parcel parcel) {
        return ParcelJpaMapper.toDomain(parcelJpaRepository.save(ParcelJpaMapper.toJpaEntity(parcel)));
    }

    @Override
    @Transactional(readOnly = true)
    public Parcel findById(Long id) {
        return parcelJpaRepository.findById(id).map(ParcelJpaMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parcel> findAllByShipmentId(Long shipmentId) {
        return parcelJpaRepository.findAllByShipmentId(shipmentId).stream()
                .map(ParcelJpaMapper::toDomain)
                .toList();
    }
}
