package co.fastshipping.model.notification.gateways;

import co.fastshipping.model.notification.Notification;

public interface EmailNotificationGateway {
    void send(Notification notification);
}
