package co.fastshipping.twilionotification.adapter;

import co.fastshipping.model.notification.Notification;
import co.fastshipping.model.notification.gateways.SmsNotificationGateway;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwilioSmsNotificationAdapter implements SmsNotificationGateway {

    private final TwilioRestClient twilioRestClient;
    private final String fromNumber;

    public TwilioSmsNotificationAdapter(
            TwilioRestClient twilioRestClient,
            @Value("${notification.twilio.from-number:}") String fromNumber) {
        this.twilioRestClient = twilioRestClient;
        this.fromNumber = fromNumber;
    }

    @Override
    public void send(Notification notification) {
        if (fromNumber == null || fromNumber.isBlank()) {
            throw new IllegalStateException("notification.twilio.from-number must be configured to send SMS");
        }

        Message message = Message.creator(
                        new PhoneNumber(notification.getRecipient()),
                        new PhoneNumber(fromNumber),
                        notification.getMessage())
                .create(twilioRestClient);

        log.info("Twilio accepted SMS with SID {}", message.getSid());
    }
}
