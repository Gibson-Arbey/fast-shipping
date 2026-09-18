package co.fastshipping.api.licensecategory.response;

public record LicenseCategoryResponse(
        Long id,
        String code,
        String name,
        String description
) {
}
