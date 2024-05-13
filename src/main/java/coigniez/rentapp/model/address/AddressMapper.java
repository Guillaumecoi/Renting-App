package coigniez.rentapp.model.address;

import java.util.ArrayList;
import java.util.List;

import coigniez.rentapp.model.exceptions.InvalidAddressException;
import coigniez.rentapp.model.interfaces.Mapper;

public class AddressMapper implements Mapper<Address, AddressDTO>{

    /**
     * Maps an Address entity to an AddressDTO
     * @param address Address entity
     * @return AddressDTO
     */
    public AddressDTO entityToDto(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setHouseNumber(address.getHouseNumber());
        dto.setBusNumber(address.getBusNumber());
        dto.setPostalCode(address.getPostalCode());
        dto.setCity(address.getCity());
        dto.setProvince(address.getProvince());
        dto.setCountry(address.getCountry());
        return dto;
    }

    /**
     * Maps a list of Address entities to a list of AddressDTOs
     * @param addresses List of Address entities
     * @return List of AddressDTOs
     */
    public List<AddressDTO> entitiesToDtos(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }

        List<AddressDTO> dtos = new ArrayList<>();
        for (Address address : addresses) {
            dtos.add(entityToDto(address));
        }

        return dtos;
    }

    /**
     * Maps an AddressDTO to an Address entity
     * @param dto AddressDTO
     * @return Address entity
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public Address dtoToEntity(AddressDTO dto) throws InvalidAddressException {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        address.setBusNumber(dto.getBusNumber());
        address.setPostalCode(dto.getPostalCode());
        address.setCity(dto.getCity());
        address.setProvince(dto.getProvince());
        address.setCountry(dto.getCountry());
        return address;
    }

    /**
     * Maps a list of AddressDTOs to a list of Address entities
     * @param dtos List of AddressDTOs
     * @return List of Address entities
     * @throws InvalidAddressException if the postal code or country is invalid
     */
    public List<Address> dtosToEntities(List<AddressDTO> dtos) throws InvalidAddressException {
        if (dtos == null) {
            return null;
        }

        List<Address> addresses = new ArrayList<>();
        for (AddressDTO dto : dtos) {
            addresses.add(dtoToEntity(dto));
        }

        return addresses;
    }
}