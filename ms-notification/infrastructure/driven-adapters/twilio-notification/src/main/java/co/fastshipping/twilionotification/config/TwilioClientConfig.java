package co.fastshipping.twilionotification.config;

import com.twilio.http.TwilioRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioClientConfig {

    @Bean
    public TwilioRestClient twilioRestClient(
            @Value("${notification.twilio.account-sid:}") String accountSid,
            @Value("${notification.twilio.auth-token:}") String authToken,
            @Value("${notification.twilio.from-number:}") String fromNumber) {
        if (accountSid.isBlank() || authToken.isBlank() || fromNumber.isBlank()) {
            throw new IllegalStateException(
                    "TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN and TWILIO_FROM_NUMBER must be configured "
                            + "to enable SMS notifications");
        }
        return new TwilioRestClient.Builder(accountSid, authToken).build();
    }
}
