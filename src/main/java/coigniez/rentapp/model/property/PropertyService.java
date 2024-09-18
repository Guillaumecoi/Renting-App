package coigniez.rentapp.model.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import coigniez.rentapp.exceptions.InvalidAddressException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper = new PropertyMapper();

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property to the database
     * 
     * @param property the PropertyDto to save
     * @return the saved property
     * @throws InvalidAddressException if the postal code or country is invalid
     * @throws ValidationException     if the name of the property is invalid
     */
    public PropertyDTO saveProperty(@Valid PropertyDTO property) throws InvalidAddressException {
        logger.info("Entering saveProperty method with property: " + property);

        Property savedProperty = propertyRepository.save(propertyMapper.dtoToEntity(property));
        logger.info("Property saved: " + savedProperty);

        PropertyDTO result = propertyMapper.entityToDto(savedProperty);
        logger.info("Returning PropertyDTO: " + result);

        return result;
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
     * @param property the PropertyDto to update
     * @return the updated property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO updateProperty(@Valid PropertyDTO property) throws InvalidAddressException {
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
     * @param property the PropertyDto to delete
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public void deleteProperty(@Valid PropertyDTO property) throws InvalidAddressException {
        propertyRepository.delete(propertyMapper.dtoToEntity(property));
    }

    /**
     * Find all tags in the database
     * 
     * @return a list of all tags
     */
    public List<String> findAllTags() {
        return propertyRepository.findDistinctTags();
    }
}