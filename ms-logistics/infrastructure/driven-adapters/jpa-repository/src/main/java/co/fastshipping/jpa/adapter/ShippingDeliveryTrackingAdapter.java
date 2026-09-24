package co.fastshipping.jpa.adapter;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.delivery.gateways.DeliveryTrackingGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class ShippingDeliveryTrackingAdapter implements DeliveryTrackingGateway {
    private final RestClient shippingClient;

    public ShippingDeliveryTrackingAdapter(
            RestClient.Builder clientBuilder,
            @Value("${services.shipping.base-url:http://localhost:8082}") String shippingBaseUrl
    ) {
        this.shippingClient = clientBuilder.baseUrl(shippingBaseUrl).build();
    }

    @Override
    public void notifyStatusChange(Delivery delivery, String location, String observation) {
        String bearerToken = getBearerToken();
        ParcelStatusRequest request = new ParcelStatusRequest(
                toParcelStatus(delivery.getStatus()),
                location == null ? "" : location,
                observation == null || observation.isBlank()
                        ? "Delivery status changed to " + delivery.getStatus().name()
                        : observation
        );

        shippingClient.patch()
                .uri("/api/parcel/{parcelId}/status", delivery.getParcelId())
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    private String getBearerToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getCredentials() instanceof String token) || token.isBlank()) {
            throw new IllegalStateException("A bearer token is required to update parcel tracking");
        }
        return token;
    }

    private String toParcelStatus(DeliveryStatus status) {
        return switch (status) {
            case PENDING -> "ASSIGNED";
            case IN_TRANSIT -> "IN_TRANSIT";
            case DELIVERED -> "DELIVERED";
            case FAILED -> "DELIVERY_FAILED";
            case CANCELLED -> "CANCELLED";
        };
    }

    public record ParcelStatusRequest(String status, String location, String observation) {
    }
}
