package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.RoleJpaMapper;
import co.fastshipping.jpa.repository.RoleJpaRepository;
import co.fastshipping.model.role.Role;
import co.fastshipping.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Role findByName(String name) {
        return RoleJpaMapper.toDomain(roleJpaRepository.findByName(name));

    }
}
