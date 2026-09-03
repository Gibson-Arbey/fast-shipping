package co.fastshipping.model.user.gateways;

import co.fastshipping.model.user.User;

public interface UserRepository {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);
}
