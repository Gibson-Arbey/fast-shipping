package co.fastshipping.api.driver.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterDriverRequest(
        @NotNull(message = "userId is required")
        @Positive(message = "userId must be greater than zero")
        Long userId,
        @NotBlank(message = "licenseNumber is required")
        @Size(max = 30, message = "licenseNumber must not exceed 30 characters")
        String licenseNumber,
        @NotEmpty(message = "licenseCategories must not be empty")
        Set<@NotBlank(message = "license category code is required")
                @Size(max = 20, message = "license category code must not exceed 20 characters") String> licenseCategories
) {
}
