package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.RoleJpaEntity;
import co.fastshipping.model.role.Role;

public class RoleJpaMapper {

    public static Role toDomain(RoleJpaEntity entity) {
        if(entity == null) {
            return null;
        }
        return Role.restore(entity.getId(), entity.getName());
    }

    public  static RoleJpaEntity toEntity(Role role) {
        if(role == null) {
            return null;
        }
        return RoleJpaEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
