package co.fastshipping.model.licensecategory;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class LicenseCategory {

    private final Long id;

    private final String code;

    private final String name;

    private final String description;

    private LicenseCategory(Long id, String code, String name, String description) {
        if (code == null || code.isBlank()) throw new InvalidFieldException("Code not valid");
        if (name == null || name.isBlank()) throw new InvalidFieldException("Name not valid");

        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static LicenseCategory create(String code, String name, String description) {
        return new LicenseCategory(null, code, name, description);
    }

    public static LicenseCategory restore(Long id, String code, String name, String description) {
        return new LicenseCategory(id, code, name, description);
    }
}
