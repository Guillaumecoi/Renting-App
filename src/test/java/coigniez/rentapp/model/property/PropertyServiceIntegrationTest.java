package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import coigniez.rentapp.model.address.Address;

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
    public void testSaveProperty() throws Exception {
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

        Property savedProperty = propertyService.saveProperty(property);

        assertNotNull(savedProperty.getId());
        assertEquals(property.getName(), savedProperty.getName());
        assertNotNull(savedProperty.getAddress());
        assertEquals(property.getAddress(), savedProperty.getAddress());
    }

    @Test
    public void testFindPropertyById() throws Exception {
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

        Optional<Property> foundProperty = propertyService.findPropertyById(savedProperty.getId());

        assertEquals(savedProperty.getId(), foundProperty.get().getId());
        assertEquals(property.getName(), foundProperty.get().getName());
        assertNotNull(foundProperty.get().getAddress());
        assertEquals(property.getAddress(), foundProperty.get().getAddress());
    }
}