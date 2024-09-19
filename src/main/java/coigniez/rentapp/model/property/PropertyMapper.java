package coigniez.rentapp.model.property;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressMapper;

@Mapper(uses = {AddressMapper.class})
public interface PropertyMapper {

    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
    AddressMapper addressMapper = AddressMapper.INSTANCE;

    @Mapping(target = "tags", expression = "java(property.getTags() != null ? new java.util.HashSet<>(property.getTags()) : null)")
    @Mapping(target = "address", expression = "java(addressMapper.toDto(property.getAddress()))")
    PropertyDTO toDto(Property property);

    @Mapping(target = "tags", expression = "java(dto.getTags() != null ? new java.util.HashSet<>(dto.getTags()) : null)")
    @Mapping(target = "address", expression = "java(addressMapper.toEntity(dto.getAddress()))")
    Property toEntity(PropertyDTO dto) throws InvalidAddressException;
}
