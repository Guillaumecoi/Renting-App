package coigniez.rentapp.services;

import coigniez.rentapp.model.Property;
import coigniez.rentapp.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    void testDeleteProperty() {
        Long id = 1L;
        propertyService.deleteProperty(id);
        verify(propertyRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAllProperties() {
        Property property1 = new Property();
        Property property2 = new Property();
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property1, property2));

        List<Property> properties = propertyService.findAllProperties();

        assertEquals(2, properties.size());
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testFindPropertyById() {
        Property property = new Property();
        property.setId(1L);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        Optional<Property> foundProperty = propertyService.findPropertyById(1L);

        assertEquals(property.getId(), foundProperty.get().getId());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveProperty() {
        Property property = new Property();
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property savedProperty = propertyService.saveProperty(property);

        assertEquals(property, savedProperty);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdateProperty() {
        Property property = new Property();
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property updatedProperty = propertyService.updateProperty(property);

        assertEquals(property, updatedProperty);
        verify(propertyRepository, times(1)).save(property);
    }
}
