package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.address.AddressDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PropertyService.class)
public class PropertyServiceIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyService propertyService;

    @Test
    public void testSaveProperty() throws Exception {
        AddressDTO address = new AddressDTO();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        PropertyDTO property = new PropertyDTO();
        property.setName("Test Property");
        property.setAddress(address);

        PropertyDTO savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty.getId());
        assertEquals(property.getName(), savedProperty.getName());
        assertNotNull(savedProperty.getAddress());
        assertNotNull(savedProperty.getAddress().getId());
        assertEquals(address.getStreet(), savedProperty.getAddress().getStreet());
        assertEquals(address.getHouseNumber(), savedProperty.getAddress().getHouseNumber());
        assertEquals(address.getBusNumber(), savedProperty.getAddress().getBusNumber());
        assertEquals(address.getPostalCode(), savedProperty.getAddress().getPostalCode());
        assertEquals(address.getCity(), savedProperty.getAddress().getCity());
        assertEquals(address.getProvince(), savedProperty.getAddress().getProvince());
        assertEquals(address.getCountry(), savedProperty.getAddress().getCountry());
    }

    @Test
    public void testFindPropertyById() throws Exception {
        // Arrange
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");

        Property property = new Property();
        property.setName("Test Property");
        property.setAddress(address);

        Property savedProperty = entityManager.persistAndFlush(property);

        // Act
        Optional<PropertyDTO> foundProperty = propertyService.findPropertyById(savedProperty.getId());

        // Assert
        assertTrue(foundProperty.isPresent());
        assertEquals(savedProperty.getId(), foundProperty.get().getId());
        assertEquals(property.getName(), foundProperty.get().getName());
        assertNotNull(foundProperty.get().getAddress());
        assertEquals(address.getId(), foundProperty.get().getAddress().getId());
        assertEquals(address.getStreet(), foundProperty.get().getAddress().getStreet());
        assertEquals(address.getHouseNumber(), foundProperty.get().getAddress().getHouseNumber());
        assertEquals(address.getBusNumber(), foundProperty.get().getAddress().getBusNumber());
        assertEquals(address.getPostalCode(), foundProperty.get().getAddress().getPostalCode());
        assertEquals(address.getCity(), foundProperty.get().getAddress().getCity());
        assertEquals(address.getProvince(), foundProperty.get().getAddress().getProvince());
        assertEquals(address.getCountry(), foundProperty.get().getAddress().getCountry());
    }

    @Test
    public void testFindPropertyByIdNotFound() {
        // Act
        Optional<PropertyDTO> foundProperty = propertyService.findPropertyById(1L);

        // Assert
        assertTrue(foundProperty.isEmpty());
    }

    @Test
    void testFindAllProperties() throws InvalidAddressException {
        // Arrange
        Property property1 = new Property();
        property1.setName("Test Property 1");

        Property property2 = new Property();
        property2.setName("Test Property 2");

        entityManager.persistAndFlush(property1);
        entityManager.persistAndFlush(property2);

        // Act
        var properties = propertyService.findAllProperties();

        // Assert
        assertEquals(2, properties.size());
        assertEquals(property1.getName(), properties.get(0).getName());
        assertEquals(property2.getName(), properties.get(1).getName());
    }

    @Test
    void testUpdateProperty() throws InvalidAddressException {
        // Arrange
        AddressDTO address = new AddressDTO();
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        PropertyDTO property = new PropertyDTO();
        property.setName("Test Property");
        property.setAddress(address);

        PropertyDTO savedProperty = propertyService.saveProperty(property);

        savedProperty.setName("Updated Property");
        savedProperty.getAddress().setStreet("Updated Street");

        // Act
        PropertyDTO updatedProperty = propertyService.updateProperty(savedProperty);

        // Assert
        assertEquals(savedProperty.getId(), updatedProperty.getId());
        assertEquals(savedProperty.getName(), updatedProperty.getName());
        assertEquals(savedProperty.getAddress().getId(), updatedProperty.getAddress().getId());
        assertEquals(savedProperty.getAddress().getStreet(), updatedProperty.getAddress().getStreet());
        assertEquals(savedProperty.getAddress().getHouseNumber(), updatedProperty.getAddress().getHouseNumber());
        assertEquals(savedProperty.getAddress().getBusNumber(), updatedProperty.getAddress().getBusNumber());
        assertEquals(savedProperty.getAddress().getPostalCode(), updatedProperty.getAddress().getPostalCode());
        assertEquals(savedProperty.getAddress().getCity(), updatedProperty.getAddress().getCity());
        assertEquals(savedProperty.getAddress().getProvince(), updatedProperty.getAddress().getProvince());
        assertEquals(savedProperty.getAddress().getCountry(), updatedProperty.getAddress().getCountry());
    }
}