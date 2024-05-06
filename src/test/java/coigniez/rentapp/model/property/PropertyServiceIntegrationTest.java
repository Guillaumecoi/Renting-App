package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(PropertyService.class)
public class PropertyServiceIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyService propertyService;

    @Test
    public void testSaveProperty() {
        Property property = new Property();
        property.setName("Test Property");

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty.getId());
        assertEquals(property.getName(), savedProperty.getName());
    }

    @Test
    public void testFindPropertyById() {
        Property property = new Property();
        property.setName("Test Property");

        Property savedProperty = entityManager.persistAndFlush(property);

        Optional<Property> foundProperty = propertyService.findPropertyById(savedProperty.getId());

        assertEquals(savedProperty.getId(), foundProperty.get().getId());
        assertEquals(property.getName(), foundProperty.get().getName());
    }
}