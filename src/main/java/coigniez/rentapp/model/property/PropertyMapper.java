package coigniez.rentapp.model.property;

import java.util.ArrayList;
import java.util.List;

import coigniez.rentapp.model.address.AddressMapper;
import coigniez.rentapp.model.exceptions.InvalidAddressException;
import coigniez.rentapp.model.interfaces.Mapper;

public class PropertyMapper implements Mapper<Property, PropertyDTO> {
    private final AddressMapper addressMapper = new AddressMapper();

    public PropertyDTO entityToDto(Property property) {
        if (property == null) {
            return null;
        }

        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setAddress(addressMapper.entityToDto(property.getAddress()));

        return dto;
    }

    public List<PropertyDTO> entitiesToDtos(List<Property> properties) {
        if (properties == null) {
            return null;
        }

        List<PropertyDTO> dtos = new ArrayList<>();
        for (Property property : properties) {
            dtos.add(entityToDto(property));
        }

        return dtos;
    }

    public Property dtoToEntity(PropertyDTO dto) throws InvalidAddressException {
        if (dto == null) {
            return null;
        }

        Property property = new Property();
        property.setId(dto.getId());
        property.setName(dto.getName());
        property.setAddress(addressMapper.dtoToEntity(dto.getAddress()));

        return property;
    }

    public List<Property> dtosToEntities(List<PropertyDTO> dtos) throws InvalidAddressException {
        if (dtos == null) {
            return null;
        }

        List<Property> properties = new ArrayList<>();
        for (PropertyDTO dto : dtos) {
            properties.add(dtoToEntity(dto));
        }

        return properties;
    }
}
