package co.fastshipping.usecase.user;

import co.fastshipping.model.role.Role;
import co.fastshipping.model.role.exception.RoleNotFoundException;
import co.fastshipping.model.role.gateways.RoleRepository;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.gateways.PasswordEncoderRepository;
import co.fastshipping.model.user.gateways.UserRepository;
import co.fastshipping.model.user.valueobject.Email;
import co.fastshipping.model.user.valueobject.Password;
import co.fastshipping.usecase.user.command.CreateUserCommand;
import co.fastshipping.usecase.user.exception.EmailAlreadyExistsException;
import co.fastshipping.usecase.user.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderRepository passwordEncoderRepository;

    public User execute(CreateUserCommand command) {

        if(userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException("User with email " + command.email() + " already exists");
        }

        Role role = roleRepository.findByName(command.roleName());

        if(role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        // Validaciones de dominio
        Email email = new Email(command.email());
        PasswordPolicy.validate(command.password());
        Password password = new Password(passwordEncoderRepository.encode(command.password()));

        return userRepository.save(User.create(
            command.name(),
            command.lastName(),
            email,
            password,
            role.getId()
        ));
    }
}
