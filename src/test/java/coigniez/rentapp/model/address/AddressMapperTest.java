package coigniez.rentapp.model.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import coigniez.rentapp.exceptions.InvalidAddressException;

public class AddressMapperTest {

    private final AddressMapper mapper = AddressMapper.INSTANCE;

    @Test
    void testDtoToEntity() throws InvalidAddressException {
        // Arrange
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("1234");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Belgium");

        // Act
        Address address = mapper.toEntity(dto);

        // Assert
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
    void testDtoToEntityInvalidCountry() {
        // Arrange
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("1234");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Invalid Country");

        // Act & Assert
        Exception exception = assertThrows(InvalidAddressException.class, () -> {
            mapper.toEntity(dto);
        });

        assertEquals("Invalid country code or name: Invalid Country", exception.getMessage());
    }

    @Test
    void testDtoToEntityInvalidPostalCode() {
        // Arrange
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Test Street");
        dto.setHouseNumber("123");
        dto.setBusNumber("1A");
        dto.setPostalCode("Invalid Postal Code");
        dto.setCity("Test City");
        dto.setProvince("Test Province");
        dto.setCountry("Belgium");

        // Act & Assert
        Exception exception = assertThrows(InvalidAddressException.class, () -> {
            mapper.toEntity(dto);
        });

        assertEquals("Invalid postal code for Belgium.", exception.getMessage());
    }

    @Test
    void testEntityToDto() throws InvalidAddressException {
        // Arrange
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");

        // Act
        AddressDTO dto = mapper.toDto(address);

        // Assert
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
