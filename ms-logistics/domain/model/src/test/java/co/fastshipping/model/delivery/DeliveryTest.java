package co.fastshipping.model.delivery;

import co.fastshipping.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryTest {
    @Test
    void deliveryStartsPending() {
        assertEquals(DeliveryStatus.PENDING, Delivery.create(1L, 2L, 3L).getStatus());
    }

    @Test
    void deliverySupportsSuccessfulLifecycle() {
        Delivery delivery = Delivery.create(1L, 2L, 3L).start().complete();

        assertEquals(DeliveryStatus.DELIVERED, delivery.getStatus());
    }

    @Test
    void deliveryCannotCompleteBeforeStarting() {
        assertThrows(InvalidFieldException.class, () -> Delivery.create(1L, 2L, 3L).complete());
        assertThrows(InvalidFieldException.class, () -> Delivery.create(1L, 2L, 3L).start().cancel());
    }
}
