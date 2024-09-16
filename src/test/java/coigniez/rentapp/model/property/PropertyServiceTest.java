package coigniez.rentapp.model.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import coigniez.rentapp.exceptions.InvalidAddressException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Property propertyEntity = new Property();
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
    void testDeletePropertyByProperty() throws InvalidAddressException {
        // Arrange
        PropertyDTO property = new PropertyDTO();
        Property propertyEntity = new Property();

        // Act
        propertyService.deleteProperty(property);

        // Assert
        verify(propertyRepository, times(1)).delete(propertyEntity);
    }

    @Test
    void testFindAllTags() {
        // Arrange
        when(propertyRepository.findDistinctTags()).thenReturn(Arrays.asList("Test Tag"));

        // Act
        List<String> tags = propertyService.findAllTags();

        // Assert
        assertEquals(1, tags.size());
        assertEquals("Test Tag", tags.get(0));
        verify(propertyRepository, times(1)).findDistinctTags();
    }
}
