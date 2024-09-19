package coigniez.rentapp.model.property;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByName(String name);

    @Query("SELECT DISTINCT t.name FROM Property p JOIN p.tags t")
    Set<String> findDistinctTags();
}
