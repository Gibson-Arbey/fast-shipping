package co.fastshipping.model.authentication.gateways;

public interface AuthenticationRepository {

    void authenticate(String email, String password);
}
