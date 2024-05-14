package coigniez.rentapp.model.property;

import org.springframework.stereotype.Service;

import coigniez.rentapp.exceptions.InvalidAddressException;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper = new PropertyMapper();

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property to the database
     * 
     * @param property the property to save
     * @return the saved property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO saveProperty(PropertyDTO property) throws InvalidAddressException {
        Property savedProperty = propertyRepository.save(propertyMapper.dtoToEntity(property));
        return propertyMapper.entityToDto(savedProperty);
    }

    /**
     * Find a property by its id
     * 
     * @param id the id of the property
     * @return the property if found
     */
    public Optional<PropertyDTO> findPropertyById(Long id) {
        return propertyRepository.findById(id).map(propertyMapper::entityToDto);
    }

    /**
     * Find all properties in the database
     * 
     * @return a list of all properties
     */
    public List<PropertyDTO> findAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return propertyMapper.entitiesToDtos(properties);
    }

    /**
     * Update a property in the database
     * 
     * @param property the property to update
     * @return the updated property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO updateProperty(PropertyDTO property) throws InvalidAddressException {
        Property updatedProperty = propertyRepository.save(propertyMapper.dtoToEntity(property));
        return propertyMapper.entityToDto(updatedProperty);
    }

    /**
     * Delete a property from the database
     * 
     * @param id the id of the property to delete
     */
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    /**
     * Delete a property from the database
     * 
     * @param property the property to delete
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public void deleteProperty(PropertyDTO property) throws InvalidAddressException {
        propertyRepository.delete(propertyMapper.dtoToEntity(property));
    }
}