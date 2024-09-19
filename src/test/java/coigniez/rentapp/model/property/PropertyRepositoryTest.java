package coigniez.rentapp.model.property;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.property.tag.Tag;

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
        property.setTags(new HashSet<>(Arrays.asList(new Tag("Test Tag"))));
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
    public void testCreatePropertyWithAddress() throws InvalidAddressException {
        // Arrange
        Property newProperty = new Property();
        newProperty.setName("New Property");
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        newProperty.setAddress(address);

        // Act
        Property savedProperty = propertyRepository.save(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
        assertNotNull(savedProperty.getAddress());
        assertEquals(address.getStreet(), savedProperty.getAddress().getStreet());
        assertEquals(address.getHouseNumber(), savedProperty.getAddress().getHouseNumber());
        assertEquals(address.getBusNumber(), savedProperty.getAddress().getBusNumber());
        assertEquals(address.getPostalCode(), savedProperty.getAddress().getPostalCode());
        assertEquals(address.getCity(), savedProperty.getAddress().getCity());
        assertEquals(address.getProvince(), savedProperty.getAddress().getProvince());
        assertEquals(address.getCountry(), savedProperty.getAddress().getCountry());
    }

    @Test
    public void testCreatePropertyWithTags() {
        // Arrange
        Property newProperty = new Property();
        newProperty.setName("New Property");
        newProperty.setTags(new HashSet<>(Arrays.asList(new Tag("New Tag"))));

        // Act
        Property savedProperty = propertyRepository.save(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
        assertNotNull(savedProperty.getTags());
        assertEquals(1, savedProperty.getTags().size());
        assertTrue(savedProperty.getTags().stream().anyMatch(tag -> tag.getName().equals("New Tag")));
    }

    @Test
    public void testReadProperty() {
        // Act
        Optional<Property> readProperty = propertyRepository.findById(property.getId());

        // Assert
        assertTrue(readProperty.isPresent());
        assertNotNull(readProperty.get().getId());
        assertEquals(property.getName(), readProperty.get().getName());
        assertNotNull(readProperty.get().getAddress());
        assertEquals(property.getAddress().getStreet(), readProperty.get().getAddress().getStreet());
        assertEquals(property.getAddress().getHouseNumber(), readProperty.get().getAddress().getHouseNumber());
        assertEquals(property.getAddress().getBusNumber(), readProperty.get().getAddress().getBusNumber());
        assertEquals(property.getAddress().getPostalCode(), readProperty.get().getAddress().getPostalCode());
        assertEquals(property.getAddress().getCity(), readProperty.get().getAddress().getCity());
        assertEquals(property.getAddress().getProvince(), readProperty.get().getAddress().getProvince());
        assertEquals(property.getAddress().getCountry(), readProperty.get().getAddress().getCountry());
        // tags
        assertNotNull(readProperty.get().getTags());
        assertEquals(1, readProperty.get().getTags().size());
        assertTrue(readProperty.get().getTags().stream().anyMatch(tag -> tag.getName().equals("Test Tag")));
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
        Set<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals("Test Tag", tags.iterator().next());
    }

    @Test
    void testFindDistinctTagsEmpty() {
        // Arrange
        property.setTags(new HashSet<>());
        propertyRepository.saveAndFlush(property);

        // Act
        Set<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertTrue(tags.isEmpty());
    }

    @Test
    void testFindDistinctTagsMultiple() {
        // Arrange
        Property property2 = new Property();
        property2.setName("Test Property 2");
        property2.setTags(new HashSet<>(Arrays.asList(new Tag("Test Tag 2"), new Tag("Test Tag 3"))));
        propertyRepository.saveAndFlush(property2);

        // Act
        Set<String> tags = propertyRepository.findDistinctTags();

        // Assert
        assertNotNull(tags);
        assertEquals(3, tags.size());
        assertTrue(tags.contains("Test Tag"));
        assertTrue(tags.contains("Test Tag 2"));
        assertTrue(tags.contains("Test Tag 3"));
    }
}
