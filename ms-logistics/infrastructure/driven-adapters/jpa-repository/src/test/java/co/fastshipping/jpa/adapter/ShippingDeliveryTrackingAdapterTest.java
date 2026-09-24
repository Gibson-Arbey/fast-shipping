package co.fastshipping.jpa.adapter;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ShippingDeliveryTrackingAdapterTest {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void forwardsParcelStatusAuditAndAuthenticatedBearerToken() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://shipping/api/parcel/70/status"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer caller-token"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"status":"IN_TRANSIT","location":"Dock 1","observation":"Loaded for delivery"}
                        """))
                .andRespond(withSuccess());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "caller-token", List.of()));
        ShippingDeliveryTrackingAdapter adapter = new ShippingDeliveryTrackingAdapter(builder, "http://shipping");
        Delivery delivery = Delivery.restore(3L, 70L, 10L, 2L, DeliveryStatus.IN_TRANSIT);

        assertDoesNotThrow(() -> adapter.notifyStatusChange(delivery, "Dock 1", "Loaded for delivery"));
        server.verify();
    }

    @Test
    void mapsPendingDeliveryToAssignedParcelAndProvidesAnObservationByDefault() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://shipping/api/parcel/70/status"))
                .andExpect(content().json("""
                        {"status":"ASSIGNED","location":"","observation":"Delivery status changed to PENDING"}
                        """))
                .andRespond(withSuccess());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "caller-token", List.of()));
        ShippingDeliveryTrackingAdapter adapter = new ShippingDeliveryTrackingAdapter(builder, "http://shipping");
        Delivery delivery = Delivery.create(70L, 10L, 2L);

        adapter.notifyStatusChange(delivery, "", null);
        server.verify();
    }
}
