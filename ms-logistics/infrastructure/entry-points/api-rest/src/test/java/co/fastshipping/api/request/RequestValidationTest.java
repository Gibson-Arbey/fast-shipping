package co.fastshipping.api.request;

import co.fastshipping.api.delivery.request.CreateDeliveryRequest;
import co.fastshipping.api.incident.request.CreateIncidentRequest;
import co.fastshipping.api.routeassignment.request.CreateRouteAssignmentRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RequestValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void routeAssignmentRequiresPositiveReferences() {
        var request = new CreateRouteAssignmentRequest(null, 0L, -1L);

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void deliveryRequiresAllReferences() {
        var request = new CreateDeliveryRequest(null, null, null);

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void incidentRequiresTypeAndDescription() {
        var request = new CreateIncidentRequest(1L, null, "", "");

        assertFalse(validator.validate(request).isEmpty());
    }
}
