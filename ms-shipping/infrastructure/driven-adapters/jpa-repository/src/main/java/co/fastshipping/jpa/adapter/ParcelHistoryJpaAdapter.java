package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ParcelHistoryJpaMapper;
import co.fastshipping.jpa.repository.ParcelHistoryJpaRepository;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.parcelhistory.gateways.ParcelHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
