package residentEvil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import residentEvil.domain.entity.Virus;

public interface VirusRepository extends JpaRepository<Virus, String> {

}
