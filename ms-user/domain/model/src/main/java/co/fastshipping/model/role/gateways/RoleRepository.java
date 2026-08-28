package co.fastshipping.model.role.gateways;

import co.fastshipping.model.role.Role;

public interface RoleRepository {

    Role findByName(String name);
}
