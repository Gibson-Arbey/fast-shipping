package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.AddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressJpaRepository extends JpaRepository<AddressJpaEntity, Long> {

    @Query("SELECT a FROM AddressJpaEntity a WHERE a.customer.id = :customerId AND a.deleted = false")
    List<AddressJpaEntity> findByCustomerId(@Param("customerId") Long customerId);

    @Modifying
    @Query("UPDATE AddressJpaEntity a SET a.deleted = true WHERE a.id = :addressId")
    void softDeleteById(@Param("addressId") Long addressId);

    @Query("SELECT EXISTS ( SELECT 1 FROM AddressJpaEntity a WHERE a.id = :addressId AND a.customer.id = :customerId AND a.deleted = false )")
    boolean existsAddress(@Param("addressId") Long addressId, @Param("customerId") Long customerId);
}
