package co.fastshipping.usecase.user;

import co.fastshipping.model.user.User;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetDriverUsersUseCase {

    private final UserRepository userRepository;

    public List<User> execute() {
        return userRepository.findAllByRoleName("DRIVER");
    }
}
