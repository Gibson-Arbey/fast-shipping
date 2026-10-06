package co.fastshipping.model.notificationevent;

import co.fastshipping.model.exception.InvalidFieldException;

public record NotificationEvent(String recipient, String subject, String message) {

    public NotificationEvent {
        if (recipient == null || recipient.isBlank()) {
            throw new InvalidFieldException("recipient not valid");
        }
        if (subject == null || subject.isBlank()) {
            throw new InvalidFieldException("subject not valid");
        }
        if (message == null || message.isBlank()) {
            throw new InvalidFieldException("message not valid");
        }
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public static NotificationEvent create(String recipient, String subject, String message) {
        return new NotificationEvent(recipient, subject, message);
    }
}
