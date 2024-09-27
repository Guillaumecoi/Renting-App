package coigniez.rentapp.model.address;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.interfaces.MapperInterface;

@Mapper
public interface AddressMapper extends MapperInterface<Address, AddressDTO> {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Override
    Address toEntity(AddressDTO dto) throws InvalidAddressException;

    @Override
    AddressDTO toDto(Address address);
}
