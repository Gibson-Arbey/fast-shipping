package co.fastshipping.security.adapter;

import co.fastshipping.model.role.gateways.RoleRepository;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        return User.builder()
                .username(user.getEmail().value())
                .password(user.getPassword().value())
                .roles(role.getName())
                .build();
    }
}