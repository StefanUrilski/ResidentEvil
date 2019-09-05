package residentEvil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import residentEvil.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAuthority(String authority);

}
