package co.fastshipping.usecase.driver.command;

import java.util.Set;

public record RegisterDriverCommand(
        Long userId,
        String licenseNumber,
        Set<String> licenseCategories
) {
}
