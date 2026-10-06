package co.fastshipping.api.address;

import co.fastshipping.usecase.address.GetNotificationEmailByAddressUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController
@RequestMapping("/internal/addresses")
public class InternalAddressApiRest {
    private static final String SERVICE_KEY_HEADER = "X-Internal-Service-Key";

    private final GetNotificationEmailByAddressUseCase getNotificationEmailByAddressUseCase;
    private final String internalApiKey;

    public InternalAddressApiRest(
            GetNotificationEmailByAddressUseCase getNotificationEmailByAddressUseCase,
            @Value("${security.internal-api-key:}") String internalApiKey) {
        this.getNotificationEmailByAddressUseCase = getNotificationEmailByAddressUseCase;
        this.internalApiKey = internalApiKey;
    }

    @GetMapping("/{addressId}/notification-email")
    public NotificationEmailResponse getNotificationEmail(
            @PathVariable Long addressId,
            @RequestHeader(name = SERVICE_KEY_HEADER, required = false) String serviceKey) {
        if (!hasValidServiceKey(serviceKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return new NotificationEmailResponse(getNotificationEmailByAddressUseCase.execute(addressId));
    }

    private boolean hasValidServiceKey(String providedKey) {
        if (internalApiKey == null || internalApiKey.isBlank() || providedKey == null || providedKey.isBlank()) {
            return false;
        }
        return MessageDigest.isEqual(
                internalApiKey.getBytes(StandardCharsets.UTF_8),
                providedKey.getBytes(StandardCharsets.UTF_8));
    }

    public record NotificationEmailResponse(String email) {
    }
}
