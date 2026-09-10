package co.fastshipping.model.address;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class Address {

    private final Long id;
    private final Long customerId;
    private final String street;
    private final String number;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String country;
    private final String postalCode;
    private final Boolean deleted;

    private Address(Long id, Long customerId, String street, String number, String neighborhood, String city, String state, String country, String postalCode, Boolean deleted) {
        if(customerId == null) {
            throw new InvalidFieldException("CustomerId must not be null");
        }
        if(street == null || street.isEmpty()) {
            throw new InvalidFieldException("Street must not be null or empty");
        }
        if(number == null || number.isEmpty()) {
            throw new InvalidFieldException("Number must not be null or empty");
        }
        if(neighborhood == null || neighborhood.isEmpty()) {
            throw new InvalidFieldException("Neighborhood must not be null or empty");
        }
        if(city == null || city.isEmpty()) {
            throw new InvalidFieldException("City must not be null or empty");
        }
        if(state == null || state.isEmpty()) {
            throw new InvalidFieldException("State must not be null or empty");
        }
        if(country == null || country.isEmpty()) {
            throw new InvalidFieldException("Country must not be null or empty");
        }
        if(postalCode == null || postalCode.isEmpty()) {
            throw new InvalidFieldException("PostalCode must not be null or empty");
        }
        this.id = id;
        this.customerId = customerId;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.deleted = deleted;
    }

    public static Address create(Long customerId, String street, String number, String neighborhood, String city, String state, String country, String postalCode) {
        return new Address(null, customerId, street, number, neighborhood, city, state, country, postalCode, false);
    }

    public static Address restore(Long id, Long customerId, String street, String number, String neighborhood, String city, String state, String country, String postalCode, Boolean deleted) {
        return new Address(id, customerId, street, number, neighborhood, city, state, country, postalCode, deleted);
    }
}
