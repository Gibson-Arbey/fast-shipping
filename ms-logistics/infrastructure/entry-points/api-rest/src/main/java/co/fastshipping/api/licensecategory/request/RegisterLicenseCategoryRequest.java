package co.fastshipping.api.licensecategory.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterLicenseCategoryRequest(
        @NotBlank(message = "code is required")
        @Size(max = 20, message = "code must not exceed 20 characters")
        String code,
        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name must not exceed 100 characters")
        String name,
        @Size(max = 255, message = "description must not exceed 255 characters")
        String description
) {
}
