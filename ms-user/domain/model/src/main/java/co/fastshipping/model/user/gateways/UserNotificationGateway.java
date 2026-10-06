package co.fastshipping.model.user.gateways;

public interface UserNotificationGateway {
    void publishWelcomeEmail(String recipient, String fullName);
}
