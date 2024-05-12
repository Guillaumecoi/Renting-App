package coigniez.rentapp.model.property;

import org.springframework.stereotype.Service;

import coigniez.rentapp.model.exceptions.InvalidAddressException;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property to the database
     * @param property the property to save 
     * @return the saved property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO saveProperty(PropertyDTO property) throws InvalidAddressException {
        Property savedProperty = propertyRepository.save(PropertyMapper.dtoToEntity(property));
        return PropertyMapper.entityToDto(savedProperty);
    }

    public Optional<Property> findPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }

    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}