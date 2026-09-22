package co.fastshipping.model.user.gateways;

import co.fastshipping.model.user.User;
import co.fastshipping.model.user.UserStatus;

import java.util.List;

public interface UserRepository {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

    List<User> findAllByRoleName(String roleName);

    User findById(Long id);

    void updateStatus(Long id, UserStatus status);
}
