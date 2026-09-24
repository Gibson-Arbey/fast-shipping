package co.fastshipping.model.parcel;

import co.fastshipping.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParcelTest {

    @Test
    void supportsTheDeliveryLifecycle() {
        Parcel parcel = Parcel.create(15L, decimal("2"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");

        Parcel assigned = parcel.assignToDelivery();
        Parcel inTransit = assigned.markInTransit();
        Parcel delivered = inTransit.markDelivered();

        assertEquals(ParcelStatus.CREATED, parcel.getStatus());
        assertEquals(ParcelStatus.ASSIGNED, assigned.getStatus());
        assertEquals(ParcelStatus.IN_TRANSIT, inTransit.getStatus());
        assertEquals(ParcelStatus.DELIVERED, delivered.getStatus());
    }

    @Test
    void supportsCancellationBeforeTransitAndDeliveryFailure() {
        Parcel parcel = Parcel.create(15L, decimal("2"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");

        assertEquals(ParcelStatus.CANCELLED, parcel.assignToDelivery().markCancelled().getStatus());
        assertEquals(ParcelStatus.DELIVERY_FAILED,
                parcel.assignToDelivery().markInTransit().markDeliveryFailed().getStatus());
    }

    @Test
    void rejectsInvalidTransitionsIncludingDeliveredBackToTransit() {
        Parcel parcel = Parcel.create(15L, decimal("2"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");
        Parcel delivered = parcel.assignToDelivery().markInTransit().markDelivered();

        assertThrows(InvalidFieldException.class, parcel::markInTransit);
        assertThrows(InvalidFieldException.class, parcel::markDelivered);
        assertThrows(InvalidFieldException.class, delivered::markInTransit);
        assertThrows(InvalidFieldException.class, delivered::markCancelled);
    }

    @Test
    void shipmentAssociationIsSingleAndOnlyAllowedBeforeAssignment() {
        Parcel parcel = Parcel.create(15L, decimal("2"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, "parcel");
        Parcel associated = parcel.associateToShipment(30L);

        assertEquals(30L, associated.getShipmentId());
        assertEquals(associated, associated.associateToShipment(30L));
        assertThrows(InvalidFieldException.class, () -> associated.associateToShipment(31L));
        assertThrows(InvalidFieldException.class, () -> associated.assignToDelivery().associateToShipment(30L));
    }

    @Test
    void validatesRequiredFieldsAndPositiveDimensions() {
        assertThrows(InvalidFieldException.class, () -> Parcel.create(0L, decimal("2"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, null));
        assertThrows(InvalidFieldException.class, () -> Parcel.create(1L, decimal("0"), decimal("10"), decimal("10"), decimal("10"),
                ClasificationTamanho.SMALL, ParcelType.STANDARD, null));
    }

    private static BigDecimal decimal(String value) {
        return new BigDecimal(value);
    }
}
