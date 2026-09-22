package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.UserJpaMapper;
import co.fastshipping.jpa.repository.UserJpaRepository;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.UserStatus;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByEmail(String email) {
        return UserJpaMapper.toDomain(userJpaRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return UserJpaMapper.toDomain(userJpaRepository.save(UserJpaMapper.toJpaEntity(user)));
    }

    @Override
    public List<User> findAllByRoleName(String roleName) {
        return userJpaRepository
                .findAllByRoleName(roleName)
                .stream()
                .map(UserJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, UserStatus status) {
        userJpaRepository.updateStatus(id, status);
    }
}
