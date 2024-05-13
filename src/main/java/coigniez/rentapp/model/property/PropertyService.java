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

    /**
     * Find a property by its id
     * @param id the id of the property
     * @return the property if found
     */
    public Optional<PropertyDTO> findPropertyById(Long id) {
        return propertyRepository.findById(id).map(PropertyMapper::entityToDto);
    }

    /**
     * Find all properties in the database
     * @return a list of all properties
     */
    public List<PropertyDTO> findAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return PropertyMapper.entitiesToDtos(properties);
    }

    /**
     * Update a property in the database
     * @param property the property to update
     * @return the updated property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO updateProperty(PropertyDTO property) throws InvalidAddressException {
        Property updatedProperty = propertyRepository.save(PropertyMapper.dtoToEntity(property));
        return PropertyMapper.entityToDto(updatedProperty);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}