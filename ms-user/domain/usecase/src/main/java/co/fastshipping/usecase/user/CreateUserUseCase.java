package co.fastshipping.usecase.user;

import co.fastshipping.model.user.User;
import co.fastshipping.model.user.gateways.UserRepository;
import co.fastshipping.model.user.valueobject.Email;
import co.fastshipping.usecase.user.command.CreateUserCommand;
import co.fastshipping.usecase.user.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public User execute(CreateUserCommand command) {

        // Validaciones de dominio
        Email mail = new Email(command.email());
        PasswordPolicy.validate(command.password());

        return userRepository.save(user);
    }
}
