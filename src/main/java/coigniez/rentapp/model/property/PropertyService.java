package coigniez.rentapp.model.property;

import org.springframework.stereotype.Service;

import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public PropertyDTO saveProperty(PropertyDTO property) throws InvalidPostalCodeException {
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