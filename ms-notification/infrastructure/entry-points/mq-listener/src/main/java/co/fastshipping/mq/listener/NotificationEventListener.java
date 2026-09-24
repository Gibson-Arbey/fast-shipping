package co.fastshipping.mq.listener;

import co.fastshipping.model.notification.Notification;
import co.fastshipping.model.notification.TypeNotification;
import co.fastshipping.model.notificationevent.NotificationEvent;
import co.fastshipping.usecase.notification.SendNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final SendNotificationUseCase sendNotificationUseCase;

    @RabbitListener(queues = "email-notification-queue")
    public void handleEmailNotification(NotificationEvent event) {
        sendNotificationUseCase.execute(Notification.create(event.getRecipient(), event.getSubject(), event.getMessage(), TypeNotification.EMAIL));
    }

    @RabbitListener(queues = "sms-notification-queue")
    public void handleSmsNotification(NotificationEvent event) {
        sendNotificationUseCase.execute(Notification.create(event.getRecipient(), event.getSubject(), event.getMessage(), TypeNotification.SMS));
    }
}
