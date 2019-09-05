package residentEvil.service;

import residentEvil.domain.model.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDv();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByRoleAuthority(String authority);
}
