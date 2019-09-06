package residentEvil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentEvil.domain.model.service.UserServiceModel;
import residentEvil.domain.model.service.UserViewServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    List<UserViewServiceModel> findAllUsers();
}
