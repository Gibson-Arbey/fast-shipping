package co.fastshipping.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CorsConfig.class)
class CorsConfigTest {

    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsConfigurationSource() {
        List<String> origins = List.of("http://example.com", "http://another.com");
        CorsConfigurationSource source = corsConfig.corsConfigurationSource(origins);
        CorsConfiguration retrievedConfig = ((UrlBasedCorsConfigurationSource) source)
                .getCorsConfigurations().get("/**");

        assertNotNull(retrievedConfig);
        assertEquals(Boolean.FALSE, retrievedConfig.getAllowCredentials());
        assertEquals(List.of("http://example.com", "http://another.com"), retrievedConfig.getAllowedOrigins());
        assertEquals(List.of("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS"), retrievedConfig.getAllowedMethods());
        assertEquals(List.of("Authorization", "Content-Type", "X-Api-Version"), retrievedConfig.getAllowedHeaders());
    }
}
