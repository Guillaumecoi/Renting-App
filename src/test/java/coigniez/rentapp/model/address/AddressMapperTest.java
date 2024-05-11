package coigniez.rentapp.model.address;

import org.junit.jupiter.api.Test;

import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    @Test
    public void testDtoToEntity() throws InvalidPostalCodeException {
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("1234");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Belgium");

        Address address = AddressMapper.dtoToEntity(dto);

        assertNotNull(address);
        assertEquals(dto.getId(), address.getId());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getHouseNumber(), address.getHouseNumber());
        assertEquals(dto.getBusNumber(), address.getBusNumber());
        assertEquals(dto.getPostalCode(), address.getPostalCode());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getProvince(), address.getProvince());
        assertEquals(dto.getCountry(), address.getCountry());
    }

    @Test
    public void testDtoToEntityInvalidCountry() {
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("1234");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Invalid Country");

        assertThrows(IllegalArgumentException.class, () -> AddressMapper.dtoToEntity(dto));
    }

    @Test
    public void testDtoToEntityInvalidPostalCode() {
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("Invalid Postal Code");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Belgium");

        assertThrows(InvalidPostalCodeException.class, () -> AddressMapper.dtoToEntity(dto));
    }



    @Test
    public void testEntityToDto() throws InvalidPostalCodeException {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");

        AddressDTO dto = AddressMapper.entityToDto(address);

        assertNotNull(dto);
        assertEquals(address.getId(), dto.getId());
        assertEquals(address.getStreet(), dto.getStreet());
        assertEquals(address.getHouseNumber(), dto.getHouseNumber());
        assertEquals(address.getBusNumber(), dto.getBusNumber());
        assertEquals(address.getPostalCode(), dto.getPostalCode());
        assertEquals(address.getCity(), dto.getCity());
        assertEquals(address.getProvince(), dto.getProvince());
        assertEquals(address.getCountry(), dto.getCountry());
    }
}