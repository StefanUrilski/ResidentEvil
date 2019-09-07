package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentEvil.common.Constants;
import residentEvil.domain.entity.Role;
import residentEvil.domain.model.service.RoleServiceModel;
import residentEvil.repository.RoleRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(ModelMapper modelMapper,
                           RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDb() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAndFlush(new Role(Constants.ROLE_USER));
            roleRepository.saveAndFlush(new Role(Constants.ROLE_MODERATOR));
            roleRepository.saveAndFlush(new Role(Constants.ROLE_ADMIN));
            roleRepository.saveAndFlush(new Role(Constants.ROLE_ROOT));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByRoleAuthority(String authority) {
        return modelMapper.map(roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
