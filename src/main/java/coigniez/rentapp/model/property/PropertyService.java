package coigniez.rentapp.model.property;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.property.tag.TagDTO;
import jakarta.validation.Valid;

/**
 * Service class for managing properties
 */
@Service
@Validated
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper = PropertyMapper.INSTANCE;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property to the database
     *
     * @param property the PropertyDto to save
     * @return the saved property
     * @throws InvalidAddressException if the postal code or country is invalid
     * @throws ValidationException if the name of the property is invalid
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PropertyDTO saveProperty(@Valid PropertyDTO property) throws InvalidAddressException {
        logger.info("Entering saveProperty method with property: " + property);

        // Save the property to the database
        Property savedProperty = propertyRepository.save(propertyMapper.toEntity(property));
        logger.info("Property saved: " + savedProperty);

        // Return the saved property
        return propertyMapper.toDto(savedProperty);
    }

    /**
     * Find a property by its id
     *
     * @param id the id of the property
     * @return the property if found
     */
    public Optional<PropertyDTO> findPropertyById(Long id) {
        logger.info("Entering findPropertyById with id: {}", id);

        Optional<PropertyDTO> result = propertyRepository.findById(id)
                .map(property -> propertyMapper.toDto(property));

        if (result.isPresent()) {
            logger.info("Property found with id: {}", id);
        } else {
            logger.warn("Property not found with id: {}", id);
        }

        return result;
    }

    /**
     * Find all properties in the database
     *
     * @return a list of all properties
     */
    @Transactional(readOnly = true)
    public List<PropertyDTO> findAllProperties() {
        logger.info("Starting findAllProperties method");

        List<Property> properties = null;
        try {
            logger.debug("Attempting to fetch properties from the database");
            properties = propertyRepository.findAll();
            logger.debug("Fetched {} properties from the database", properties.size());
        } catch (Exception e) {
            logger.error("Error fetching properties from the database", e);
        }

        if (properties == null) {
            logger.warn("No properties found in the database");
            return Collections.emptyList();
        }

        List<PropertyDTO> propertyDTOs = properties.stream()
                .map(property -> {
                    PropertyDTO dto = propertyMapper.toDto(property);
                    logger.debug("Mapped Property to PropertyDTO: {}", dto);
                    return dto;
                })
                .collect(Collectors.toList());

        logger.info("Completed findAllProperties method with {} properties", propertyDTOs.size());
        return propertyDTOs;
    }

    /**
     * Update a property in the database
     *
     * @param property the PropertyDto to update
     * @return the updated property
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public PropertyDTO updateProperty(@Valid PropertyDTO property) throws InvalidAddressException {
        if (property.getId() == null) {
            logger.error("A property must have an id to be updated");
            throw new IllegalArgumentException("A property must have an id to be updated");
        }
        return saveProperty(property);
    }

    /**
     * Delete a property from the database
     *
     * @param id the id of the property to delete
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProperty(Long id) {
        logger.info("Deleting property with id: {}", id);
        try {
            propertyRepository.deleteById(id);
            logger.info("Deleted property with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting property with id: {}", id, e);
        }
    }

    /**
     * Find all tags in the database
     *
     * @return a list of all tags
     */
    @Transactional(readOnly = true)
    public Set<TagDTO> findAllTags() {
        logger.info("Finding all tags in the database");
        List<String> tags = propertyRepository.findDistinctTags();
        logger.info("Found {} tags in the database", tags.size());
        return tags.stream()
                .map(tag -> new TagDTO(tag))
                .collect(Collectors.toSet());
    }
}
