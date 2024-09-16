package coigniez.rentapp.model.property;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByName(String name);

    @Query("SELECT DISTINCT p.tags FROM Property p")
    List<String> findDistinctTags();
}
