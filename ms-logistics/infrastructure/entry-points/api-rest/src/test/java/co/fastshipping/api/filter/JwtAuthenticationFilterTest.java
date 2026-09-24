package co.fastshipping.api.filter;

import co.fastshipping.api.config.SecurityConstant;
import co.fastshipping.api.util.JwtUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAuthenticationFilterTest {

    private static final String SECRET = "9c9c48d57077710e8a8ec4a72f0c959fa11ea2c2e1073b7c7d39b4eefff5ddf8";
    private static final String ISSUER = "AUTH0JWT-BACKEND";

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        SecurityConstant securityConstant = new SecurityConstant();
        securityConstant.setJwtKeyPrivate(SECRET);
        securityConstant.setJwtUserGenerator(ISSUER);
        filter = new JwtAuthenticationFilter(new JwtUtil(securityConstant));
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void authenticatesFromJwtClaimsAndIgnoresUserHeaders() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token("ROLE_CUSTOMER"));
        request.addHeader("X-User-Id", "999");
        request.addHeader("X-User-Email", "attacker@example.com");
        request.addHeader("X-User-Role", "ROLE_ADMIN");

        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean();
        FilterChain chain = (req, res) -> chainCalled.set(true);

        filter.doFilter(request, response, chain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = assertInstanceOf(UserAuthentication.class, authentication.getPrincipal());
        assertEquals(42L, user.userId());
        assertEquals("real@example.com", user.email());
        assertEquals("ROLE_CUSTOMER", user.role());
        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER")));
        assertInstanceOf(String.class, authentication.getCredentials());
        assertTrue(chainCalled.get());
    }

    @Test
    void rejectsInvalidBearerTokenWithUnauthorized() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean();
        FilterChain chain = (req, res) -> chainCalled.set(true);

        filter.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        assertFalse(chainCalled.get());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private String token(String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject("real@example.com")
                .withClaim("userId", 42L)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60_000))
                .sign(Algorithm.HMAC256(SECRET));
    }
}
