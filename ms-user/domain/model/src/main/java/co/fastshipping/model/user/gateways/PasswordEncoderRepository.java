package co.fastshipping.model.user.gateways;

public interface PasswordEncoderRepository {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
