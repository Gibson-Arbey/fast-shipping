package co.fastshipping.security.adapter;

import co.fastshipping.model.role.gateways.RoleRepository;
import co.fastshipping.model.user.gateways.UserRepository;
import co.fastshipping.model.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        var role = roleRepository.findById(user.getRoleId());
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user with email: " + email);
        }
        String authority = role.getName().toUpperCase(Locale.ROOT).startsWith("ROLE_")
                ? role.getName().toUpperCase(Locale.ROOT)
                : "ROLE_" + role.getName().toUpperCase(Locale.ROOT);

        return User.builder()
                .username(user.getEmail().value())
                .password(user.getPassword().value())
                .authorities(authority)
                .disabled(user.getStatus() != UserStatus.ACTIVE)
                .build();
    }
}
