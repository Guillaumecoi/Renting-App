package coigniez.rentapp.model.property;

import java.util.ArrayList;
import java.util.List;

import coigniez.rentapp.model.address.AddressMapper;
import coigniez.rentapp.model.exceptions.InvalidAddressException;

public class PropertyMapper {

    public static PropertyDTO entityToDto(Property property) {
        if (property == null) {
            return null;
        }

        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setAddress(AddressMapper.entityToDto(property.getAddress()));

        return dto;
    }

    public static List<PropertyDTO> entitiesToDtos(List<Property> properties) {
        if (properties == null) {
            return null;
        }

        List<PropertyDTO> dtos = new ArrayList<>();
        for (Property property : properties) {
            dtos.add(entityToDto(property));
        }

        return dtos;
    }

    public static Property dtoToEntity(PropertyDTO dto) throws InvalidAddressException {
        if (dto == null) {
            return null;
        }

        Property property = new Property();
        property.setId(dto.getId());
        property.setName(dto.getName());
        property.setAddress(AddressMapper.dtoToEntity(dto.getAddress()));

        return property;
    }

    public static List<Property> dtosToEntities(List<PropertyDTO> dtos) throws InvalidAddressException {
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
