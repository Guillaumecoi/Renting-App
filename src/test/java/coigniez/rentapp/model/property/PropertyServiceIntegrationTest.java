package coigniez.rentapp.model.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
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

    @BeforeEach
    public void setUp() throws InvalidAddressException {
        AddressDTO address = new AddressDTO();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");

        property = new PropertyDTO();
        property.setName("Test Property");
        property.setAddress(address);

        property = propertyService.saveProperty(property);
    }

    @AfterEach
    public void tearDown() {
        propertyRepository.deleteAll();
    }

    @Test
    public void testCreateProperty() throws Exception {
        // Arrange
        PropertyDTO newProperty = new PropertyDTO();
        newProperty.setName("New Property");

        // Act
        PropertyDTO savedProperty = propertyService.saveProperty(newProperty);

        // Assert
        assertNotNull(savedProperty.getId());
        assertEquals(newProperty.getName(), savedProperty.getName());
    }

    @Test
    public void testReadProperty() {
        // Act
        Optional<PropertyDTO> readProperty = propertyService.findPropertyById(property.getId());

        // Assert
        assertTrue(readProperty.isPresent());
    }

    @Test
    public void testUpdateProperty() throws Exception {
        // Arrange
        property.setName("Updated Property");

        // Act
        PropertyDTO updatedProperty = propertyService.updateProperty(property);

        // Assert
        assertEquals("Updated Property", updatedProperty.getName());
    }

    @Test
    public void testDeletePropertyById() {
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
}