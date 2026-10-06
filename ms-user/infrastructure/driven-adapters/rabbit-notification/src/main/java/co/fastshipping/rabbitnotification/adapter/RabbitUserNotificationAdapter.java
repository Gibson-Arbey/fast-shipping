package co.fastshipping.rabbitnotification.adapter;

import co.fastshipping.model.user.gateways.UserNotificationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitUserNotificationAdapter implements UserNotificationGateway {

    private static final String NOTIFICATION_EXCHANGE = "notification-events";
    private static final String EMAIL_ROUTING_KEY = "notification.email";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishWelcomeEmail(String recipient, String fullName) {
        NotificationMessage message = new NotificationMessage(
                recipient,
                "Bienvenido a Fast Shipping",
                "Hola " + fullName + ", tu cuenta de Fast Shipping fue creada exitosamente.");

        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, EMAIL_ROUTING_KEY, message);
    }
}
