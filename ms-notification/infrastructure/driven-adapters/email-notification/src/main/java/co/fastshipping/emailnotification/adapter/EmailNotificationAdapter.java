package co.fastshipping.emailnotification.adapter;

import co.fastshipping.model.notification.Notification;
import co.fastshipping.model.notification.gateways.EmailNotificationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationAdapter implements EmailNotificationGateway {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailNotificationAdapter(
            JavaMailSender mailSender,
            @Value("${spring.mail.username:}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    @Override
    public void send(Notification notification) {
        if (fromAddress == null || fromAddress.isBlank()) {
            throw new IllegalStateException("spring.mail.username must be configured to send email");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(notification.getRecipient());
        mailMessage.setSubject(notification.getSubject());
        mailMessage.setText(notification.getMessage());
        mailSender.send(mailMessage);
        log.info("Email notification sent");
    }
}
