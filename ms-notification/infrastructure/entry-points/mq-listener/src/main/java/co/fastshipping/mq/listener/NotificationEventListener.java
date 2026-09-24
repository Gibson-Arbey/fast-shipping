package co.fastshipping.mq.listener;

import co.fastshipping.model.notificationevent.NotificationEvent;
import co.fastshipping.usecase.notification.SendEmailNotificationUseCase;
import co.fastshipping.usecase.notification.SendSmsNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;
    private final SendSmsNotificationUseCase sendSmsNotificationUseCase;

    @RabbitListener(queues = "email-notification-queue")
    public void handlEmailNotification(NotificationEvent event){
        sendEmailNotificationUseCase.execute(event.getRecipient(), event.getSubject(), event.getMessage());
    }

    @RabbitListener(queues = "sms-notification-queue")
    public void handleSmsNotification(NotificationEvent event) {
        sendSmsNotificationUseCase.execute(event.getRecipient(), event.getRecipient(), event.getMessage());
    }
}
