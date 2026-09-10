package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.AddressJpaEntity;
import co.fastshipping.jpa.entity.UserJpaEntity;
import co.fastshipping.model.address.Address;

public class AddressJpaMapper {

    public static Address toDomain(AddressJpaEntity entity) {
        if(entity == null) {
            return null;
        }
        return Address.restore(
                entity.getId(),
                entity.getCustomer().getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getPostalCode(),
                entity.getDeleted()
        );
    }

    public static AddressJpaEntity toEntity(Address address) {
        if(address == null) {
            return null;
        }
        return AddressJpaEntity.builder()
                .id(address.getId())
                .customer(UserJpaEntity.builder().id(address.getCustomerId()).build())
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .deleted(address.getDeleted())
                .build();
    }
}
