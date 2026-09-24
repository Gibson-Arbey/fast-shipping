package co.fastshipping.api.parcel.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateParcelStatusRequest(
        @NotBlank(message = "status is required") String status,
        @Size(max = 255, message = "location cannot exceed 255 characters") String location,
        @Size(max = 500, message = "observation cannot exceed 500 characters") String observation
) {
}
