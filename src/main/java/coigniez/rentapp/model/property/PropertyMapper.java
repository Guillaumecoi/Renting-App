package coigniez.rentapp.model.property;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressMapper;
import coigniez.rentapp.model.property.tag.TagMapper;

@Mapper(uses = {AddressMapper.class, TagMapper.class})
public interface PropertyMapper {

    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
    AddressMapper addressMapper = AddressMapper.INSTANCE;
    TagMapper tagMapper = TagMapper.INSTANCE;

    @Mapping(target = "tags", expression = "java(tagMapper.toDtoSet(property.getTags()))")
    @Mapping(target = "address", expression = "java(addressMapper.toDto(property.getAddress()))")
    PropertyDTO toDto(Property property);

    @Mapping(target = "tags", expression = "java(tagMapper.toEntitySet(dto.getTags()))")
    @Mapping(target = "address", expression = "java(addressMapper.toEntity(dto.getAddress()))")
    Property toEntity(PropertyDTO dto) throws InvalidAddressException;
}
