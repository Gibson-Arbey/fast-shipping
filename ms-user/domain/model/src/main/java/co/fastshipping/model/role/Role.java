package co.fastshipping.model.role;

import lombok.Getter;

@Getter
public class Role {

    private final Long id;

    private final String name;

    private Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role restore(Long id, String name) {
        return new Role(id, name);
    }
}
