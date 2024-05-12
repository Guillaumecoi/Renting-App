package coigniez.rentapp.model.address;

import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;

public class AddressMapper {

    /**
     * Maps an AddressDTO to an Address entity
     * @param dto AddressDTO
     * @return Address entity
     * @throws InvalidPostalCodeException if the postal code is invalid
     */
    public static Address dtoToEntity(AddressDTO dto) throws InvalidPostalCodeException {
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
     * Maps an Address entity to an AddressDTO
     * @param address Address entity
     * @return AddressDTO
     */
    public static AddressDTO entityToDto(Address address) {
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
}