package co.fastshipping.model.user;

import co.fastshipping.model.user.valueobject.Email;
import co.fastshipping.model.user.valueobject.Password;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {

    private Long id;

    private final String name;

    private final String lastName;

    private final Email email;

    private final Password password;

    private final Long roleId;

    private final UserStatus status;

    private final LocalDateTime createdAt;


    private User(Long id, String name, String lastName, Email email, Password password, Long roleId, UserStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static User create(String name, String lastName, Email email, Password password,  Long roleId) {
        return new User(
                null,
                name,
                lastName,
                email,
                password,
                roleId,
                UserStatus.ACTIVE,
                LocalDateTime.now()
        );

    }

    public static User restore(Long id, String name, String lastName, Email email, Password password, Long roleId, UserStatus status, LocalDateTime createdDate) {
        return new User(
                id,
                name,
                lastName,
                email,
                password,
                roleId,
                status,
                createdDate
        );
    }


}
