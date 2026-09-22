package co.fastshipping.usecase.user;

import co.fastshipping.model.user.UserStatus;
import co.fastshipping.model.user.exception.UserNotExistsException;
import co.fastshipping.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStatusUserUseCase {

    private final UserRepository userRepository;

    public void execute(Long id, String status) {
        if (userRepository.findById(id) == null) {
            throw new UserNotExistsException("User not found: " + id);
        }

        userRepository.updateStatus(id, UserStatus.fromString(status));
    }
}
