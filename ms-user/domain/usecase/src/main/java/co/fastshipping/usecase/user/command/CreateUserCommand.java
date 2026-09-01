package co.fastshipping.usecase.user.command;

public record CreateUserCommand(String name, String lastName, String email, String password) {
}
