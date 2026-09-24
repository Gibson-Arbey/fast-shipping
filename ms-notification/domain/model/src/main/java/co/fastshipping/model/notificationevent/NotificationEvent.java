package co.fastshipping.model.notificationevent;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class NotificationEvent {

    private final String recipient;
    private final String subject;
    private final String message;

    private NotificationEvent(String recipient, String subject, String message) {
        if (recipient == null || recipient.isBlank()) throw new InvalidFieldException("recipient not valid");
        if (subject == null || subject.isBlank()) throw new InvalidFieldException("subject not valid");
        if (message == null || message.isBlank()) throw new InvalidFieldException("message not valid");

        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public static NotificationEvent create(String recipient, String subject, String message) {
        return new NotificationEvent(recipient, subject, message);
    }
}
