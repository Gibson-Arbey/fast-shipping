package co.fastshipping.usecase.authentication;

import co.fastshipping.model.authentication.Authentication;
import co.fastshipping.model.authentication.gateways.AuthenticationRepository;
import co.fastshipping.model.authentication.gateways.AuthenticationTokenRepository;
import co.fastshipping.model.role.Role;
import co.fastshipping.model.role.exception.RoleNotFoundException;
import co.fastshipping.model.role.gateways.RoleRepository;
import co.fastshipping.model.user.User;
import co.fastshipping.model.user.exception.UserNotExistsException;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationUseCase {

    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Authentication login(String email, String password) {

        // 1. Validar las credenciales
        authenticationRepository.authenticate(email, password);

        // 2. Buscar el usuario
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotExistsException("User with email " + email + " does not exist");
        }

        // 3. Buscar el rol
        Role role = roleRepository.findById(user.getRoleId());
        if(role == null) {
            throw new RoleNotFoundException("Role not found for user with email " + email);
        }

        // 4. Generar token
        String token = authenticationTokenRepository.generateToken(
                user.getId(),
                user.getEmail().value(),
                role.getName()
        );

        // 5. Construir respuesta
        return Authentication.create(
                user.getId(),
                user.getName() + " " + user.getLastName(),
                user.getEmail().value(),
                role.getName(),
                token
        );
    }
}
