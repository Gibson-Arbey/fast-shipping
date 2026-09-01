package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.RoleJpaEntity;
import co.fastshipping.jpa.entity.UserJpaEntity;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.valueobject.Email;
import co.fastshipping.model.user.valueobject.Password;

public class UserJpaMapper {

    public static UserJpaEntity toEntity(User user) {
        if(user == null) {
            return null;
        }
        return UserJpaEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail().value())
                .password(user.getPassword().value())
                .role(RoleJpaEntity.builder().id(user.getRoleId()).build())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User toDomain(UserJpaEntity user) {
        if(user == null) {
            return null;
        }
        return User.restore(
                user.getId(),
                user.getName(),
                user.getLastName(),
                new Email(user.getEmail()),
                new Password(user.getPassword()),
                user.getRole().getId(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
