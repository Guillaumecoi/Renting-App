package coigniez.rentapp.model.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.Address;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property;

    @BeforeEach
    public void setUp() throws InvalidAddressException {
        property = new Property();
        property.setName("Test Property");
        // set address here
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        property.setAddress(address);
        // set tags here
        property.setTags(new HashSet<>(Arrays.asList("Test Tag")));
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

    @Test
    void testFindDistinctTags() {
        // Act
        List<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals("Test Tag", tags.get(0));
    }

    @Test
    void testFindDistinctTagsEmpty() {
        // Arrange
        property.setTags(new HashSet<>());
        propertyRepository.saveAndFlush(property);

        // Act
        List<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertTrue(tags.isEmpty());
    }

    @Test
    void testFindDistinctTagsMultiple() {
        // Arrange
        Property property2 = new Property();
        property2.setName("Test Property 2");
        property2.setTags(new HashSet<>(Arrays.asList("Test Tag 2", "Test Tag 3")));
        property2 = propertyRepository.saveAndFlush(property2);

        // Act
        List<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertEquals(3, tags.size());
        assertTrue(tags.contains("Test Tag"));
        assertTrue(tags.contains("Test Tag 2"));
        assertTrue(tags.contains("Test Tag 3"));
    }
}