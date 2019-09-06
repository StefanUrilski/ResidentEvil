package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentEvil.common.Constants;
import residentEvil.domain.entity.Role;
import residentEvil.domain.entity.User;
import residentEvil.domain.model.service.UserServiceModel;
import residentEvil.domain.model.service.UserViewServiceModel;
import residentEvil.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceIml implements UserService {

    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceIml(ModelMapper modelMapper,
                          RoleService roleService,
                          BCryptPasswordEncoder encoder,
                          UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        roleService.seedRolesInDv();

        Set<Role> roles = new HashSet<>();
        if (userRepository.count() == 0) {
            roles = roleService.findAllRoles().stream()
                    .map(role -> modelMapper.map(role, Role.class))
                    .collect(Collectors.toSet());
        } else {
            roles.add(modelMapper.map(
                    roleService.findByRoleAuthority(Constants.ROLE_USER),
                    Role.class
            ));
        }

        User user = modelMapper.map(userServiceModel, User.class);

        user.setAuthorities(roles);
        user.setPassword(encoder.encode(user.getPassword()));

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
        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Override
    public List<UserViewServiceModel> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserViewServiceModel.class))
                .collect(Collectors.toList());
    }
}
