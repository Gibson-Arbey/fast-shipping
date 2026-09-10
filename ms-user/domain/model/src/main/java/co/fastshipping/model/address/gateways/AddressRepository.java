package co.fastshipping.model.address.gateways;

import co.fastshipping.model.address.Address;

import java.util.List;

public interface AddressRepository {

    Address save(Address address);

    List<Address> findByCustomerId(Long customerId);

    Address findById(Long addressId);

    boolean existsByIdAndCustomerId(Long addressId, Long customerId);

    void deleteById(Long addressId);
}
