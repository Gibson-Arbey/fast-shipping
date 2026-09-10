package co.fastshipping.usecase.address.command;

public record RegisterAddressCommand(String street, String number, String neighborhood, String city, String state, String country, String postalCode) {
}
