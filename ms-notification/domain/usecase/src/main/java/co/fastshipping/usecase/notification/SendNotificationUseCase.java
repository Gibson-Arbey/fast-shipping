package co.fastshipping.usecase.notification;

import co.fastshipping.model.notification.Notification;
import co.fastshipping.model.notification.TypeNotification;
import co.fastshipping.model.notification.gateways.EmailNotificationGateway;
import co.fastshipping.model.notification.gateways.SmsNotificationGateway;
import co.fastshipping.model.exception.InvalidFieldException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotificationUseCase {
    private final EmailNotificationGateway emailNotificationGateway;
    private final SmsNotificationGateway smsNotificationGateway;

    public void execute(Notification notification) {
        if (notification == null) {
            throw new InvalidFieldException("notification cannot be null");
        }

        if (notification.getType() == TypeNotification.EMAIL) {
            emailNotificationGateway.send(notification);
            return;
        }

        if (notification.getType() == TypeNotification.SMS) {
            smsNotificationGateway.send(notification);
            return;
        }

        throw new InvalidFieldException("notification type is not supported");
    }
}
