package co.fastshipping.api.address.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterAddressRequest(
        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "Number is required")
        String number,

        @NotBlank(message = "Neighborhood is required")
        String neighborhood,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "Postal code is required")
        String postalCode) {
}
