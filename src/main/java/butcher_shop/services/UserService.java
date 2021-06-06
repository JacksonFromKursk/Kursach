package butcher_shop.services;

import butcher_shop.controllers.UserRegistrationDto;
import butcher_shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByLogin(String login);

    void save(UserRegistrationDto registration, String cookie);
}
