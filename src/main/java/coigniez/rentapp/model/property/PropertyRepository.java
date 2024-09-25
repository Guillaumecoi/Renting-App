package coigniez.rentapp.model.property;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByName(String name);

    @Query("SELECT p FROM Property p WHERE parent.id = :id")
    List<Property> findChildren(@Param("id") Long id);

    @Query("SELECT p FROM Property p WHERE parent IS NULL")
    List<Property> findRootProperties();

    @Query(value = "SELECT DISTINCT tag_name FROM property_tags", nativeQuery = true)
    List<String> findDistinctTags();
}
