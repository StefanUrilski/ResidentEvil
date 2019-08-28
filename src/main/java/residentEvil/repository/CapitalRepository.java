package residentEvil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import residentEvil.domain.entity.Capital;

import java.util.List;

public interface CapitalRepository extends JpaRepository<Capital, String> {

    @Query("select c from Capital as c order by c.name asc ")
    List<Capital> findAllOrOrderByName();


}
