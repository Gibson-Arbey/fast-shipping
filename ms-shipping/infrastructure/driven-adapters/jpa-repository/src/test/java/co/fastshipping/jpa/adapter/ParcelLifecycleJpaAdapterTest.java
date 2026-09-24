package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.entity.ParcelHistoryJpaEntity;
import co.fastshipping.jpa.entity.ParcelJpaEntity;
import co.fastshipping.jpa.repository.ParcelHistoryJpaRepository;
import co.fastshipping.jpa.repository.ParcelJpaRepository;
import co.fastshipping.jpa.repository.ShipmentJpaRepository;
import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.ShipmentStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParcelLifecycleJpaAdapterTest {

    @Test
    void savesParcelHistoryAndDerivedShipmentStatusTogether() {
        ParcelJpaRepository parcelRepository = mock(ParcelJpaRepository.class);
        ParcelHistoryJpaRepository historyRepository = mock(ParcelHistoryJpaRepository.class);
        ShipmentJpaRepository shipmentRepository = mock(ShipmentJpaRepository.class);
        when(parcelRepository.save(any())).thenAnswer(invocation -> {
            ParcelJpaEntity entity = invocation.getArgument(0);
            entity.setId(15L);
            return entity;
        });
        when(historyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(shipmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ParcelLifecycleJpaAdapter adapter = new ParcelLifecycleJpaAdapter(
                parcelRepository, historyRepository, shipmentRepository);
        Parcel parcel = Parcel.create(20L, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE,
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");
        Shipment shipment = Shipment.restore(30L, 5L, LocalDateTime.now(), ShipmentStatus.PROCESSING);

        Parcel saved = adapter.saveWithHistory(parcel, 7L, "Dock 1", "Delivery assigned", shipment);

        ArgumentCaptor<ParcelHistoryJpaEntity> historyCaptor = ArgumentCaptor.forClass(ParcelHistoryJpaEntity.class);
        verify(historyRepository).save(historyCaptor.capture());
        assertEquals(15L, saved.getId());
        assertEquals(15L, historyCaptor.getValue().getParcel().getId());
        assertEquals(ParcelStatus.CREATED, historyCaptor.getValue().getStatus());
        assertEquals(7L, historyCaptor.getValue().getUserId());
        assertEquals("Dock 1", historyCaptor.getValue().getLocation());
        assertEquals("Delivery assigned", historyCaptor.getValue().getObservation());
        assertNotNull(historyCaptor.getValue().getCreatedAt());
        verify(shipmentRepository).save(any());
    }

    @Test
    void doesNotWriteShipmentWhenParcelDoesNotBelongToOne() {
        ParcelJpaRepository parcelRepository = mock(ParcelJpaRepository.class);
        ParcelHistoryJpaRepository historyRepository = mock(ParcelHistoryJpaRepository.class);
        ShipmentJpaRepository shipmentRepository = mock(ShipmentJpaRepository.class);
        when(parcelRepository.save(any())).thenAnswer(invocation -> {
            ParcelJpaEntity entity = invocation.getArgument(0);
            entity.setId(15L);
            return entity;
        });
        when(historyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ParcelLifecycleJpaAdapter adapter = new ParcelLifecycleJpaAdapter(
                parcelRepository, historyRepository, shipmentRepository);
        Parcel parcel = Parcel.create(20L, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE,
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");

        adapter.saveWithHistory(parcel, 7L, "", "Parcel registered", null);

        verify(historyRepository).save(any());
        verify(shipmentRepository, never()).save(any());
    }
}
