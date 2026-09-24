package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ParcelHistoryJpaMapper;
import co.fastshipping.jpa.mapper.ParcelJpaMapper;
import co.fastshipping.jpa.mapper.ShipmentJpaMapper;
import co.fastshipping.jpa.repository.ParcelHistoryJpaRepository;
import co.fastshipping.jpa.repository.ParcelJpaRepository;
import co.fastshipping.jpa.repository.ShipmentJpaRepository;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.shipment.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ParcelLifecycleJpaAdapter implements ParcelLifecycleRepository {
    private final ParcelJpaRepository parcelJpaRepository;
    private final ParcelHistoryJpaRepository parcelHistoryJpaRepository;
    private final ShipmentJpaRepository shipmentJpaRepository;

    @Override
    @Transactional
    public Parcel saveWithHistory(Parcel parcel, Long userId, String location, String observation, Shipment shipmentToUpdate) {
        var savedParcel = parcelJpaRepository.save(ParcelJpaMapper.toJpaEntity(parcel));
        ParcelHistory history = ParcelHistory.create(
                savedParcel.getId(), parcel.getStatus(), userId, location, observation);
        parcelHistoryJpaRepository.save(ParcelHistoryJpaMapper.toJpaEntity(history));
        if (shipmentToUpdate != null) {
            shipmentJpaRepository.save(ShipmentJpaMapper.toJpaEntity(shipmentToUpdate));
        }
        return ParcelJpaMapper.toDomain(savedParcel);
    }
}
