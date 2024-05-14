package coigniez.rentapp.model.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property;

    @BeforeEach
    public void setUp() {
        property = new Property();
        property.setName("Test Property");
        // set properties here
        property = propertyRepository.saveAndFlush(property);
    }

    @AfterEach
    public void tearDown() {
        propertyRepository.deleteAll();
    }

    @Test
    public void testCreateProperty() {
        // Arrange
        Property newProperty = new Property();
        newProperty.setName("New Property");

        // Act
        Property savedProperty = propertyRepository.save(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
    }

    @Test
    public void testReadProperty() {
        // Act
        Optional<Property> readProperty = propertyRepository.findById(property.getId());

        // Assert
        assertTrue(readProperty.isPresent());
    }

    @Test
    public void testUpdateProperty() {
        // Arrange
        property.setName("Updated Property");

        // Act
        propertyRepository.saveAndFlush(property);

        // Assert
        Property updatedProperty = propertyRepository.findById(property.getId()).orElse(null);
        assertEquals("Updated Property", updatedProperty.getName());
    }

    @Test
    public void testDeleteProperty() {
        // Arrange
        Long id = property.getId();

        // Act
        propertyRepository.delete(property);

        // Assert
        Optional<Property> deletedProperty = propertyRepository.findById(id);
        assertFalse(deletedProperty.isPresent());
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long id = property.getId();

        // Act
        propertyRepository.deleteById(id);

        // Assert
        Optional<Property> deletedProperty = propertyRepository.findById(id);
        assertFalse(deletedProperty.isPresent());
    }

    @Test
    void testFindByName() {
        // Act
        Optional<Property> foundProperty = propertyRepository.findByName(property.getName());

        // Assert
        assertTrue(foundProperty.isPresent());
    }
}