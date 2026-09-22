package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.UserJpaEntity;
import co.fastshipping.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    UserJpaEntity findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserJpaEntity u WHERE u.role.name = :roleName")
    List<UserJpaEntity> findAllByRoleName(@Param("roleName") String roleName);

    @Modifying
    @Query("UPDATE UserJpaEntity SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") UserStatus status);
}
