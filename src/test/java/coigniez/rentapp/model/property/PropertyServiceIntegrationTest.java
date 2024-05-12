package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

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
}