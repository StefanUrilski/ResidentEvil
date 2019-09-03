package residentEvil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentEvil.domain.model.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);


}
