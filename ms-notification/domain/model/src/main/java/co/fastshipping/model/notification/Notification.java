package co.fastshipping.model.notification;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class Notification {

    private final String recipient;
    private final String subject;
    private final String message;
    private final TypeNotification type;

    private Notification(String recipient, String subject, String message, TypeNotification type) {
        if (recipient == null || recipient.isBlank()) {
            throw new InvalidFieldException("recipient not valid");
        }
        if (subject == null || subject.isBlank()) {
            throw new InvalidFieldException("subject not valid");
        }
        if (message == null || message.isBlank()) {
            throw new InvalidFieldException("message not valid");
        }
        if (type == null) {
            throw new InvalidFieldException("type not valid");
        }

        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
    }

    public static Notification create(String recipient, String subject, String message, TypeNotification type) {
        return new Notification(recipient, subject, message, type);
    }
}
