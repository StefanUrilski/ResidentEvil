package residentEvil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import residentEvil.domain.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    UserRole findByAuthority(String authority);

}
