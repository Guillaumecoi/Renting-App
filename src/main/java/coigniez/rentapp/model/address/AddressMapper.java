package coigniez.rentapp.model.address;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import coigniez.rentapp.exceptions.InvalidAddressException;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toEntity(AddressDTO dto) throws InvalidAddressException;

    AddressDTO toDto(Address address);
}
