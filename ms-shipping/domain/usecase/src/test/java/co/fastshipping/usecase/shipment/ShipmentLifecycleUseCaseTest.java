package co.fastshipping.usecase.shipment;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.parcel.exception.ParcelNotFoundException;
import co.fastshipping.model.parcel.gateways.ParcelLifecycleRepository;
import co.fastshipping.model.parcel.gateways.ParcelRepository;
import co.fastshipping.model.parcelhistory.ParcelHistory;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.ShipmentStatus;
import co.fastshipping.model.shipment.ShipmentStatusResolver;
import co.fastshipping.model.shipment.exception.ShipmentNotFoundException;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import co.fastshipping.usecase.parcel.UpdateParcelStatusUseCase;
import co.fastshipping.usecase.shipment.command.CreateShipmentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShipmentLifecycleUseCaseTest {
    private ParcelRepository parcelRepository;
    private ParcelLifecycleRepository lifecycleRepository;
    private ShipmentRepository shipmentRepository;
    private Map<Long, Parcel> parcels;
    private List<ParcelHistory> history;
    private Shipment shipment;

    @BeforeEach
    void setUp() {
        parcelRepository = mock(ParcelRepository.class);
        lifecycleRepository = mock(ParcelLifecycleRepository.class);
        shipmentRepository = mock(ShipmentRepository.class);
        parcels = new HashMap<>();
        history = new ArrayList<>();
        shipment = Shipment.restore(100L, 50L, LocalDateTime.now(), ShipmentStatus.CREATED);

        when(shipmentRepository.findById(100L)).thenAnswer(invocation -> shipment);
        when(shipmentRepository.save(any())).thenAnswer(invocation -> {
            shipment = invocation.getArgument(0);
            return shipment;
        });
        when(parcelRepository.findById(any())).thenAnswer(invocation -> parcels.get(invocation.getArgument(0)));
        when(parcelRepository.findAllByShipmentId(100L)).thenAnswer(invocation -> parcels.values().stream()
                .filter(parcel -> Objects.equals(parcel.getShipmentId(), 100L)).toList());
        when(parcelRepository.save(any())).thenAnswer(invocation -> {
            Parcel saved = invocation.getArgument(0);
            parcels.put(saved.getId(), saved);
            return saved;
        });
        when(lifecycleRepository.saveWithHistory(any(), any(), any(), any(), nullable(Shipment.class))).thenAnswer(invocation -> {
            Parcel savedParcel = invocation.getArgument(0);
            Long userId = invocation.getArgument(1);
            String location = invocation.getArgument(2);
            String observation = invocation.getArgument(3);
            Shipment changedShipment = invocation.getArgument(4);
            parcels.put(savedParcel.getId(), savedParcel);
            history.add(ParcelHistory.create(savedParcel.getId(), savedParcel.getStatus(), userId, location, observation));
            if (changedShipment != null) {
                shipment = changedShipment;
            }
            return savedParcel;
        });
    }

    @Test
    void createsAssociatesAndTracksMultipleParcelsUntilShipmentCompletes() {
        CreateShipmentUseCase createShipment = new CreateShipmentUseCase(shipmentRepository);
        when(shipmentRepository.save(any())).thenAnswer(invocation -> {
            Shipment saved = invocation.getArgument(0);
            shipment = Shipment.restore(100L, saved.getSenderAddressId(), saved.getCreatedAt(), saved.getStatus());
            return shipment;
        });
        Shipment created = createShipment.execute(new CreateShipmentCommand(50L));
        assertEquals(ShipmentStatus.CREATED, created.getStatus());

        parcels.put(1L, newParcel(1L));
        parcels.put(2L, newParcel(2L));
        AssociateParcelToShipmentUseCase associate = new AssociateParcelToShipmentUseCase(
                parcelRepository, shipmentRepository, new ShipmentStatusResolver());
        associate.execute(1L, 100L);
        associate.execute(2L, 100L);

        UpdateParcelStatusUseCase updateStatus = updateStatusUseCase();
        updateStatus.execute(1L, ParcelStatus.ASSIGNED, 7L, "", "Delivery assigned");
        assertEquals(ShipmentStatus.PROCESSING, shipment.getStatus());
        updateStatus.execute(1L, ParcelStatus.IN_TRANSIT, 7L, "Hub A", "Delivery started");
        updateStatus.execute(1L, ParcelStatus.DELIVERED, 7L, "Address A", "Delivery completed");
        assertEquals(ShipmentStatus.PROCESSING, shipment.getStatus());
        updateStatus.execute(2L, ParcelStatus.ASSIGNED, 7L, "", "Delivery assigned");
        updateStatus.execute(2L, ParcelStatus.IN_TRANSIT, 7L, "Hub B", "Delivery started");
        updateStatus.execute(2L, ParcelStatus.DELIVERED, 7L, "Address B", "Delivery completed");

        assertEquals(ShipmentStatus.COMPLETED, shipment.getStatus());
        assertEquals(6, history.size());
        assertEquals(ParcelStatus.DELIVERED, history.get(5).getStatus());
        assertEquals(7L, history.get(5).getUserId());
        assertEquals("Address B", history.get(5).getLocation());
        assertEquals("Delivery completed", history.get(5).getObservation());
    }

    @Test
    void retriesOfTheCurrentStatusDoNotCreateDuplicateHistory() {
        parcels.put(1L, newParcel(1L).associateToShipment(100L));
        UpdateParcelStatusUseCase updateStatus = updateStatusUseCase();

        updateStatus.execute(1L, ParcelStatus.ASSIGNED, 7L, "", "Delivery assigned");
        updateStatus.execute(1L, ParcelStatus.ASSIGNED, 7L, "", "Delivery assigned");

        assertEquals(1, history.size());
        verify(lifecycleRepository).saveWithHistory(any(), any(), any(), any(), nullable(Shipment.class));
    }

    @Test
    void invalidStatusAndMissingEntitiesAreRejectedWithoutHistory() {
        UpdateParcelStatusUseCase updateStatus = updateStatusUseCase();

        assertThrows(ParcelNotFoundException.class,
                () -> updateStatus.execute(9L, ParcelStatus.ASSIGNED, 7L, "", "assigned"));
        parcels.put(1L, newParcel(1L));
        assertThrows(InvalidFieldException.class,
                () -> updateStatus.execute(1L, ParcelStatus.DELIVERED, 7L, "", "invalid"));
        verify(lifecycleRepository, never()).saveWithHistory(any(), any(), any(), any(), nullable(Shipment.class));
    }

    @Test
    void missingShipmentCannotAcceptParcelAssociation() {
        when(shipmentRepository.findById(404L)).thenReturn(null);
        AssociateParcelToShipmentUseCase associate = new AssociateParcelToShipmentUseCase(
                parcelRepository, shipmentRepository, new ShipmentStatusResolver());

        assertThrows(ShipmentNotFoundException.class, () -> associate.execute(1L, 404L));
    }

    private UpdateParcelStatusUseCase updateStatusUseCase() {
        return new UpdateParcelStatusUseCase(
                parcelRepository, lifecycleRepository, shipmentRepository, new ShipmentStatusResolver());
    }

    private static Parcel newParcel(Long id) {
        return Parcel.restore(id, UUID.randomUUID(), 60L, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE,
                BigDecimal.ONE, ClasificationTamanho.SMALL, ParcelType.STANDARD, ParcelStatus.CREATED,
                "parcel", null);
    }
}
