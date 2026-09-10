package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.AddressJpaMapper;
import co.fastshipping.jpa.repository.AddressJpaRepository;
import co.fastshipping.model.address.Address;
import co.fastshipping.model.address.gateways.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressJpaAdapter implements AddressRepository {

    private final AddressJpaRepository addressJpaRepository;

    @Override
    @Transactional
    public Address save(Address address) {
        return AddressJpaMapper.toDomain(addressJpaRepository.save(AddressJpaMapper.toEntity(address)));
    }

    @Override
    public List<Address> findByCustomerId(Long customerId) {
        return addressJpaRepository.findByCustomerId(customerId).stream()
                .map(AddressJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Address findById(Long addressId) {
        return AddressJpaMapper.toDomain(addressJpaRepository.findById(addressId).orElse(null));
    }

    @Override
    public boolean existsByIdAndCustomerId(Long addressId, Long customerId) {
        return false;
    }

    @Override
    @Transactional
    public void deleteById(Long addressId) {
        addressJpaRepository.softDeleteById(addressId);
    }
}
