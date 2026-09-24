package co.fastshipping.model.notification.gateways;

import co.fastshipping.model.notification.Notification;

public interface SmsNotificationGateway {
    void send(Notification notification);
}
