package co.fastshipping.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {

    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsFilter() {
        List<String> origins = List.of("http://example.com", "http://another.com");
        CorsConfigurationSource source = corsConfig.corsConfigurationSource(origins);
        CorsConfiguration retrievedConfig = ((org.springframework.web.cors.UrlBasedCorsConfigurationSource) source)
                .getCorsConfigurations().get("/**");

        assertNotNull(retrievedConfig);
        assertEquals(Boolean.FALSE, retrievedConfig.getAllowCredentials());
        assertEquals(List.of("http://example.com", "http://another.com"), retrievedConfig.getAllowedOrigins());
        assertEquals(List.of("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS"), retrievedConfig.getAllowedMethods());
        assertEquals(List.of("Authorization", "Content-Type", "X-Api-Version"), retrievedConfig.getAllowedHeaders());
    }
}
