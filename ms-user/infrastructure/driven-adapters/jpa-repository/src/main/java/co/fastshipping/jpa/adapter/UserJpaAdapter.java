package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.UserJpaMapper;
import co.fastshipping.jpa.repository.UserJpaRepository;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return UserJpaMapper.toDomain(userJpaRepository.save(UserJpaMapper.toEntity(user)));
    }
}
