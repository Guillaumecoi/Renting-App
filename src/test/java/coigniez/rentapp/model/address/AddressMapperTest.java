package coigniez.rentapp.model.address;

import org.junit.jupiter.api.Test;

import coigniez.rentapp.model.exceptions.InvalidAddressException;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();

    @Test
    public void testDtoToEntity() throws InvalidAddressException {
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("1234");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Belgium");

        Address address = addressMapper.dtoToEntity(dto);

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
    public void testDtoToEntityNull() throws InvalidAddressException {
        assertNull(addressMapper.dtoToEntity(null));
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

        assertThrows(InvalidAddressException.class, () -> addressMapper.dtoToEntity(dto));
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

        assertThrows(InvalidAddressException.class, () -> addressMapper.dtoToEntity(dto));
    }

    @Test
    public void testEntityToDto() throws InvalidAddressException {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");

        AddressDTO dto = addressMapper.entityToDto(address);

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

    @Test
    public void testEntityToDtoNull() {
        assertNull(addressMapper.entityToDto(null));
    }
}