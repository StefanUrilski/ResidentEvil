package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentEvil.common.Constants;
import residentEvil.domain.entity.User;
import residentEvil.domain.entity.UserRole;
import residentEvil.domain.model.service.UserServiceModel;
import residentEvil.repository.UserRepository;
import residentEvil.repository.UserRoleRepository;


@Service
public class UserServiceIml implements UserService {

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;

    @Autowired
    public UserServiceIml(ModelMapper modelMapper,
                          BCryptPasswordEncoder encoder,
                          UserRepository userRepository,
                          UserRoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            UserRole user = new UserRole();
            user.setAuthority(Constants.ROLE_USER);

            UserRole moderator = new UserRole();
            moderator.setAuthority(Constants.ROLE_MODERATOR);

            UserRole admin = new UserRole();
            admin.setAuthority(Constants.ROLE_ADMIN);

            UserRole root = new UserRole();
            root.setAuthority(Constants.ROLE_ROOT);

            this.roleRepository.save(root);
            this.roleRepository.save(admin);
            this.roleRepository.save(moderator);
            this.roleRepository.save(user);
        }
    }

    private UserRole role() {
        long count = userRepository.count();
        UserRole userRole;

        if (count == 0) {
            userRole = roleRepository.findByAuthority(Constants.ROLE_ROOT);
        } else if (count == 1) {
            userRole = roleRepository.findByAuthority(Constants.ROLE_ADMIN);
        } else if (count == 2) {
            userRole = roleRepository.findByAuthority(Constants.ROLE_MODERATOR);
        } else {
            userRole = roleRepository.findByAuthority(Constants.ROLE_USER);
        }

        return userRole;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        seedRoles();

        User user = modelMapper.map(userServiceModel, User.class);

        user.setPassword(encoder.encode(user.getPassword()));
        user.getAuthorities().add(role());

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

}
