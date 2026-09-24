package co.fastshipping.model.shipment;

import co.fastshipping.model.parcel.ClasificationTamanho;
import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.ParcelStatus;
import co.fastshipping.model.parcel.ParcelType;
import co.fastshipping.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipmentStatusResolverTest {
    private final ShipmentStatusResolver resolver = new ShipmentStatusResolver();
    private final Shipment shipment = Shipment.restore(1L, 2L, LocalDateTime.now(), ShipmentStatus.CREATED);

    @Test
    void resolvesEmptyAndAllCreatedShipmentsAsCreated() {
        assertEquals(ShipmentStatus.CREATED, resolver.resolve(shipment, List.of()));
        assertEquals(ShipmentStatus.CREATED, resolver.resolve(shipment, List.of(parcel(ParcelStatus.CREATED), parcel(ParcelStatus.CREATED))));
    }

    @Test
    void completesOnlyWhenEveryParcelIsDelivered() {
        assertEquals(ShipmentStatus.COMPLETED,
                resolver.resolve(shipment, List.of(parcel(ParcelStatus.DELIVERED), parcel(ParcelStatus.DELIVERED))));
    }

    @Test
    void cancelsOnlyWhenEveryParcelIsCancelled() {
        assertEquals(ShipmentStatus.CANCELLED,
                resolver.resolve(shipment, List.of(parcel(ParcelStatus.CANCELLED), parcel(ParcelStatus.CANCELLED))));
    }

    @Test
    void everyMixedOrLegacyStatusCombinationRemainsProcessing() {
        for (ParcelStatus first : ParcelStatus.values()) {
            for (ParcelStatus second : ParcelStatus.values()) {
                if (first == second && (first == ParcelStatus.CREATED
                        || first == ParcelStatus.DELIVERED || first == ParcelStatus.CANCELLED)) {
                    continue;
                }
                assertEquals(ShipmentStatus.PROCESSING,
                        resolver.resolve(shipment, List.of(parcel(first), parcel(second))),
                        first + " with " + second);
            }
        }
    }

    @Test
    void rejectsMissingShipmentOrParcelList() {
        assertThrows(InvalidFieldException.class, () -> resolver.resolve(null, List.of()));
        assertThrows(InvalidFieldException.class, () -> resolver.resolve(shipment, null));
    }

    private static Parcel parcel(ParcelStatus status) {
        return Parcel.restore(1L, UUID.randomUUID(), 1L, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE,
                BigDecimal.ONE, ClasificationTamanho.SMALL, ParcelType.STANDARD, status, "parcel", 1L);
    }
}
