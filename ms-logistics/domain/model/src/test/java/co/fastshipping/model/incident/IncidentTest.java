package co.fastshipping.model.incident;

import co.fastshipping.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncidentTest {
    @Test
    void incidentStartsOpenAndCanBeResolved() {
        Incident incident = Incident.create(1L, null, IncidentType.TRAFFIC_DELAY, "Traffic", 10L);
        Incident resolved = incident.resolve(LocalDateTime.now());

        assertEquals(IncidentStatus.RESOLVED, resolved.getStatus());
        assertNotNull(resolved.getResolvedAt());
    }

    @Test
    void incidentRequiresDescriptionAndReporter() {
        assertThrows(InvalidFieldException.class, () -> Incident.create(1L, null, IncidentType.OTHER, "", 10L));
        assertThrows(InvalidFieldException.class, () -> Incident.create(1L, null, IncidentType.OTHER, "Issue", null));
    }

    @Test
    void resolvedIncidentCannotBeResolvedAgain() {
        Incident incident = Incident.create(1L, null, IncidentType.OTHER, "Issue", 10L)
                .resolve(LocalDateTime.now());

        assertThrows(InvalidFieldException.class, () -> incident.resolve(LocalDateTime.now()));
    }
}
