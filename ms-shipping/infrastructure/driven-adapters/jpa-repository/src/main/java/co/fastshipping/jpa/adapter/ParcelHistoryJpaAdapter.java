package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ParcelHistoryJpaMapper;
import co.fastshipping.jpa.repository.ParcelHistoryJpaRepository;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.parcelhistory.gateways.ParcelHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ParcelHistoryJpaAdapter implements ParcelHistoryRepository {

    private final ParcelHistoryJpaRepository parcelHistoryJpaRepository;

    @Override
    @Transactional
    public ParcelHistory save(ParcelHistory parcelHistory) {
        return ParcelHistoryJpaMapper.toDomain(
                parcelHistoryJpaRepository.save(ParcelHistoryJpaMapper.toJpaEntity(parcelHistory))
        );
    }

    @Override
    public List<ParcelHistory> findAllByFilters(Long parcelId, LocalDateTime fromDate, LocalDateTime toDate, Boolean applyStatus, ParcelStatus status) {
        return parcelHistoryJpaRepository
                .findAllByParcelId(parcelId, fromDate, toDate, applyStatus, status)
                .stream()
                .map(ParcelHistoryJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
