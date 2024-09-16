package coigniez.rentapp.model.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressDTO;

@DataJpaTest
@ActiveProfiles("test")
@Import(PropertyService.class)
public class PropertyServiceIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    private PropertyDTO property;
    private AddressDTO address;
    private Set<String> tags;

    @BeforeEach
    void setUp() throws InvalidAddressException {
        // Arrange
        property = new PropertyDTO();
        property.setName("Test Property");

        // set address here
        address = new AddressDTO();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        property.setAddress(address);
        // set tags here
        tags = Set.of("tag1", "tag2");
        property.setTags(tags);

        property = propertyService.saveProperty(property);
    }

    @AfterEach
    void tearDown() {
        propertyRepository.deleteAll();
    }

    @Test
    void testCreateProperty() throws Exception {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");

        // Act
        PropertyDTO savedProperty = propertyService.saveProperty(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
        assertNull(newProperty.getAddress());
        assertNull(newProperty.getTags());
    }

    @Test
    void testReadProperty() {
        // Act
        Optional<PropertyDTO> readProperty = propertyService.findPropertyById(property.getId());

        // Assert
        assertTrue(readProperty.isPresent());
        PropertyDTO foundProperty = readProperty.get();

        assertEquals(property.getId(), foundProperty.getId());
        assertEquals(property.getName(), foundProperty.getName());
        assertEquals(property.getAddress(), foundProperty.getAddress());
        assertEquals(property.getTags(), foundProperty.getTags());
    }

    @Test
    void testUpdateProperty() throws Exception {
        // Arrange
        property.setName("Updated Property");

        // Act
        PropertyDTO updatedProperty = propertyService.updateProperty(property);

        // Assert
        assertEquals("Updated Property", updatedProperty.getName());
    }

    @Test
    void testDeletePropertyById() {
        // Arrange
        Long id = property.getId();

        // Act
        propertyService.deleteProperty(id);

        // Assert
        Optional<PropertyDTO> deletedProperty = propertyService.findPropertyById(id);
        assertFalse(deletedProperty.isPresent());
    }

    @Test
    void testDeleteProperty() throws InvalidAddressException {
        // Act
        propertyService.deleteProperty(property);

        // Assert
        Optional<PropertyDTO> deletedProperty = propertyService.findPropertyById(property.getId());
        assertFalse(deletedProperty.isPresent());
    }

    @Test
    void testFindAllTags() {
        // Act
        List<String> distinctTags = propertyService.findAllTags();

        // Assert
        assertEquals(2, distinctTags.size());
        assertTrue(distinctTags.containsAll(tags));
    }

    @Test
    void testFindAllTagsEmpty() {
        // Arrange
        propertyRepository.deleteAll();

        // Act
        List<String> distinctTags = propertyService.findAllTags();

        // Assert
        assertTrue(distinctTags.isEmpty());
    }

    @Test
    void testFindAllTagsDistinct() throws InvalidAddressException {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");
        newProperty.setTags(Set.of("tag1", "tag3"));
        propertyService.saveProperty(newProperty);

        // Act
        List<String> distinctTags = propertyService.findAllTags();

        // Assert
        assertEquals(3, distinctTags.size());
        assertTrue(distinctTags.containsAll(Set.of("tag1", "tag2", "tag3")));
    }
}