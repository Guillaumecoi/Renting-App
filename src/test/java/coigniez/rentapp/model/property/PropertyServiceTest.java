package coigniez.rentapp.model.property;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.tag.TagDTO;

public class PropertyServiceTest {

    private PropertyRepository propertyRepository;
    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        propertyRepository = Mockito.mock(PropertyRepository.class);
        propertyService = new PropertyService(propertyRepository);
    }

    @Test
    void testSaveProperty() throws InvalidAddressException {
        // Arrange
        PropertyDTO property = new PropertyDTO();
        Property propertyEntity = new Property();
        when(propertyRepository.save(any(Property.class))).thenReturn(propertyEntity);

        // Act
        PropertyDTO savedProperty = propertyService.saveProperty(property);

        // Assert
        assertEquals(property, savedProperty);
        verify(propertyRepository, times(1)).save(propertyEntity);
    }

    @Test
    void testSavePropertyWithInvalidAddress() {
        // Arrange
        PropertyDTO property = new PropertyDTO();
        property.setAddress(new AddressDTO());
        property.getAddress().setCountry("Invalid Country");

        // Act & Assert
        Exception exception = assertThrows(InvalidAddressException.class, () -> propertyService.saveProperty(property));
        assertEquals("Invalid country code or name: Invalid Country", exception.getMessage());
    }

    @Test
    void testFindPropertyById() {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        // Act
        Optional<PropertyDTO> foundProperty = propertyService.findPropertyById(1L);

        // Assert
        assertEquals(property.getId(), foundProperty.get().getId());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllProperties() {
        // Arrange
        Property property1 = new Property();
        Property property2 = new Property();
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property1, property2));

        // Act
        List<PropertyDTO> properties = propertyService.findAllProperties();

        // Assert
        assertEquals(2, properties.size());
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProperty() throws InvalidAddressException {
        // Arrange
        PropertyDTO property = new PropertyDTO();
        property.setId(1L);
        Property propertyEntity = new Property();
        propertyEntity.setId(1L);
        when(propertyRepository.save(any(Property.class))).thenReturn(propertyEntity);

        // Act
        PropertyDTO updatedProperty = propertyService.updateProperty(property);

        // Assert
        assertEquals(property, updatedProperty);
        verify(propertyRepository, times(1)).save(propertyEntity);
    }

    @Test
    void testDeletePropertyById() {
        // Arrange
        Long id = 1L;

        // Act
        propertyService.deleteProperty(id);

        // Assert
        verify(propertyRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAllTags() {
        // Arrange
        when(propertyRepository.findDistinctTags()).thenReturn(List.of("Test Tag"));

        // Act
        Set<TagDTO> tags = propertyService.findAllTags();

        // Assert
        assertEquals(1, tags.size());
        assertEquals("Test Tag", tags.iterator().next().getName());
        verify(propertyRepository, times(1)).findDistinctTags();
    }
}
