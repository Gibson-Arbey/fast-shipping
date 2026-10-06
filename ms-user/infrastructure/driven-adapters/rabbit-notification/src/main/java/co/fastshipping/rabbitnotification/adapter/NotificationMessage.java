package co.fastshipping.rabbitnotification.adapter;

public record NotificationMessage(String recipient, String subject, String message) {
}
