package co.fastshipping.usecase.licensecategory.command;

public record RegisterLicenseCategoryCommand(
        String code,
        String name,
        String description
) {
}
