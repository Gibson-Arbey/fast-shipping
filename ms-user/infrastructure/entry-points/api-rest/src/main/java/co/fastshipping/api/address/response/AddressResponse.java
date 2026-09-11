package co.fastshipping.api.address.response;

public record AddressResponse(Long id, Long customerId, String street, String number, String neighborhood, String city, String state, String country, String postalCode) {
}
