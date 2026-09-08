package co.fastshipping.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class RequestUtil {

    private RequestUtil() {
    }

    private static HttpServletRequest getRequest() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();

        if (attributes == null) {
            throw new IllegalStateException(
                    "No HTTP request available"
            );
        }

        return attributes.getRequest();
    }

    public static Long getUserId() {

        String userId = getRequest()
                .getHeader("X-User-Id");

        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException(
                    "X-User-Id header is missing"
            );
        }

        return Long.valueOf(userId);
    }

    public static String getEmail() {
        return getRequest()
                .getHeader("X-User-Email");
    }

    public static String getRole() {
        return getRequest()
                .getHeader("X-User-Role");
    }
}