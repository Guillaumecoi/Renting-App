package coigniez.rentapp.model.property;

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
}
