package co.fastshipping.rabbitnotification.adapter;

import co.fastshipping.model.parcel.Parcel;
import co.fastshipping.model.parcel.gateways.ParcelStatusNotificationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class RabbitParcelStatusNotificationAdapter implements ParcelStatusNotificationGateway {
    private static final String NOTIFICATION_EXCHANGE = "notification-events";
    private static final String EMAIL_ROUTING_KEY = "notification.email";

    private final RabbitTemplate rabbitTemplate;
    private final RestClient userClient;
    private final String internalServiceApiKey;

    public RabbitParcelStatusNotificationAdapter(
            RabbitTemplate rabbitTemplate,
            RestClient.Builder restClientBuilder,
            @Value("${services.user.base-url:http://localhost:8081}") String userServiceBaseUrl,
            @Value("${services.user.internal-api-key:}") String internalServiceApiKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.userClient = restClientBuilder.baseUrl(userServiceBaseUrl).build();
        this.internalServiceApiKey = internalServiceApiKey;
    }

    @Override
    public void notifyStatusChanged(Parcel parcel) {
        if (internalServiceApiKey == null || internalServiceApiKey.isBlank()) {
            log.warn("Skipping parcel status email for parcel {}: internal user service key is not configured", parcel.getId());
            return;
        }

        try {
            NotificationEmailResponse contact = userClient.get()
                    .uri("/internal/addresses/{addressId}/notification-email", parcel.getDestinationAddressId())
                    .header("X-Internal-Service-Key", internalServiceApiKey)
                    .retrieve()
                    .body(NotificationEmailResponse.class);

            if (contact == null || contact.email() == null || contact.email().isBlank()) {
                log.warn("Skipping parcel status email for parcel {}: address owner has no email", parcel.getId());
                return;
            }

            String subject = "Actualización de tu paquete";
            String message = "Tu paquete con guía " + parcel.getTrackingNumber()
                    + " cambió al estado " + parcel.getStatus().name() + ".";
            rabbitTemplate.convertAndSend(
                    NOTIFICATION_EXCHANGE,
                    EMAIL_ROUTING_KEY,
                    new NotificationMessage(contact.email(), subject, message));
        } catch (RestClientException | AmqpException exception) {
            // Notification delivery is secondary to the persisted parcel lifecycle update.
            log.error("Could not publish status email for parcel {}", parcel.getId(), exception);
        }
    }

    public record NotificationEmailResponse(String email) {
    }

    public record NotificationMessage(String recipient, String subject, String message) {
    }
}
