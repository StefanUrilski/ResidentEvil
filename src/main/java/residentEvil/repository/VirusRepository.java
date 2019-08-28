package residentEvil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import residentEvil.domain.entity.Virus;

public interface VirusRepository extends JpaRepository<Virus, String> {

    @Transactional
    @Modifying
    @Query(value = "delete from resident_evil.viruses_capitals " +
            "where virus_id = :virusId and capital_id = :capitalId", nativeQuery = true)
    void removeByCapitalsId(@Param("virusId")String virusId,@Param("capitalId") String capitalId);
}
