package coigniez.rentapp.model.property;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.tag.TagDTO;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;

@DataJpaTest
@ActiveProfiles("test")
@Import({PropertyService.class})
public class PropertyServiceIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private EntityManager entityManager;

    private PropertyDTO property;
    private AddressDTO address;
    private Set<TagDTO> tags;

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
        tags = Set.of(new TagDTO("tag1"), new TagDTO("tag2"));
        property.setTags(tags);

        property = propertyService.saveProperty(property);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
        propertyRepository.deleteAll();
    }

    @Test
    void testCreateProperty() {
        // Assert
        assertNotNull(property.getId());
        assertEquals("Test Property", property.getName());
        assertEquals("Test Street", property.getAddress().getStreet());
        assertEquals("123", property.getAddress().getHouseNumber());
        assertEquals("1A", property.getAddress().getBusNumber());
        assertEquals("1234", property.getAddress().getPostalCode());
        assertEquals("Test City", property.getAddress().getCity());
        assertEquals("Test Province", property.getAddress().getProvince());
        assertEquals("Belgium", property.getAddress().getCountry());
        assertEquals(tags, property.getTags());
    }

    @Test
    void testCreatePropertyOnlyName() throws Exception {
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
    void testSavePropertyWithInvalidName() {
        // Arrange
        PropertyDTO emptyName = new PropertyDTO();
        emptyName.setName("");

        PropertyDTO nullName = new PropertyDTO();

        // Act & Assert
        assertThrows(ValidationException.class, () -> propertyService.saveProperty(emptyName));
        assertThrows(ValidationException.class, () -> propertyService.saveProperty(nullName));
    }

    @Test
    void testCreatePropertyWithoutTags() throws Exception {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");
        newProperty.setAddress(address);

        // Act
        PropertyDTO savedProperty = propertyService.saveProperty(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
        assertEquals("Test Street", savedProperty.getAddress().getStreet());
        assertEquals("123", savedProperty.getAddress().getHouseNumber());
        assertEquals("1A", savedProperty.getAddress().getBusNumber());
        assertEquals("1234", savedProperty.getAddress().getPostalCode());
        assertEquals("Test City", savedProperty.getAddress().getCity());
        assertEquals("Test Province", savedProperty.getAddress().getProvince());
        assertEquals("Belgium", savedProperty.getAddress().getCountry());
        assertNull(savedProperty.getTags());
    }

    @Test
    void testCreatePropertyWithoutAddress() throws Exception {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");
        newProperty.setTags(tags);

        // Act
        PropertyDTO savedProperty = propertyService.saveProperty(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
        assertNull(newProperty.getAddress());
        assertEquals(tags, savedProperty.getTags());
    }

    @Test
    void testCreatePropertyWithParent() throws Exception {
        // Arrange
        PropertyDTO parentProperty = new PropertyDTO();
        parentProperty.setName("Parent Property");
        PropertyDTO savedParent = propertyService.saveProperty(parentProperty);

        PropertyDTO child = new PropertyDTO();
        child.setName("Child Property");
        child.setParent(savedParent);

        // Act
        PropertyDTO savedChild = propertyService.saveProperty(child);

        // Assert
        assertNotNull(savedChild.getId(), "Child property ID should not be null");
        assertEquals(child.getName(), savedChild.getName(), "Child property name should match");
        assertNotNull(savedChild.getParent(), "Child property parent should not be null");
        assertEquals(savedParent.getId(), savedChild.getParent().getId(), "Parent property ID should match");
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
    void testFindAllProperties() throws InvalidAddressException {
        // Arrange
        PropertyDTO property1 = new PropertyDTO();
        property1.setName("Property 1");
        property1.setParent(property);
        property1 = propertyService.saveProperty(property1);

        PropertyDTO property2 = new PropertyDTO();
        property2.setName("Property 2");
        property2.setParent(property1);
        propertyService.saveProperty(property2);

        PropertyDTO property3 = new PropertyDTO();
        property3.setName("Property 3");
        propertyService.saveProperty(property3);

        // Act
        List<PropertyDTO> properties = propertyService.findAllProperties();

        // Assert
        assertEquals(4, properties.size());
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Test Property")));
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Property 1")));
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Property 2")));
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Property 3")));
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
    void testGetRootProperties() throws InvalidAddressException {
        // Arrange
        PropertyDTO property1 = new PropertyDTO();
        property1.setName("Property 1");
        property1.setParent(property);
        property1 = propertyService.saveProperty(property1);

        PropertyDTO property2 = new PropertyDTO();
        property2.setName("Property 2");
        property2.setParent(property1);
        propertyService.saveProperty(property2);

        PropertyDTO property3 = new PropertyDTO();
        property3.setName("Property 3");
        propertyService.saveProperty(property3);

        // Act
        List<PropertyDTO> properties = propertyService.getRootProperties();

        // Assert
        assertEquals(2, properties.size());
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Test Property")));
        assertTrue(properties.stream().anyMatch(prop -> prop.getName().equals("Property 3")));
    }

    @Test
    void testFindAllChildren() throws InvalidAddressException {
        // Arrange
        PropertyDTO child1 = new PropertyDTO();
        child1.setName("Child Property 1");
        child1.setParent(property);
        propertyService.saveProperty(child1);

        PropertyDTO child2 = new PropertyDTO();
        child2.setName("Child Property 2");
        child2.setParent(property);
        propertyService.saveProperty(child2);

        // Act
        List<PropertyDTO> children = propertyService.getChildren(property.getId());

        // Assert
        assertEquals(2, children.size());
        assertTrue(children.stream().anyMatch(child -> child.getName().equals("Child Property 1")));
        assertTrue(children.stream().anyMatch(child -> child.getName().equals("Child Property 2")));
    }

    @Test
    void testFindAllTags() {
        // Act
        Set<TagDTO> distinctTags = propertyService.findAllTags();

        // Assert
        assertEquals(2, distinctTags.size());
        assertTrue(distinctTags.stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(distinctTags.stream().anyMatch(tag -> tag.getName().equals("tag2")));
    }

    @Test
    void testFindAllTagsEmpty() {
        // Arrange
        propertyRepository.deleteAll();

        // Act
        Set<TagDTO> distinctTags = propertyService.findAllTags();

        // Assert
        assertTrue(distinctTags.isEmpty());
    }

    @Test
    void testFindAllTagsDistinct() throws InvalidAddressException {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");
        newProperty.setTags(Set.of(new TagDTO("tag1"), new TagDTO("tag3")));
        propertyService.saveProperty(newProperty);

        // Act
        Set<TagDTO> distinctTags = propertyService.findAllTags();

        // Assert
        assertEquals(3, distinctTags.size());
        assertTrue(distinctTags.stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(distinctTags.stream().anyMatch(tag -> tag.getName().equals("tag2")));
        assertTrue(distinctTags.stream().anyMatch(tag -> tag.getName().equals("tag3")));
    }
}
