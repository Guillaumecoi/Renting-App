package coigniez.rentapp.model.address;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import coigniez.rentapp.model.exceptions.InvalidAddressException;

public class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();

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
        Address address = addressMapper.dtoToEntity(dto);

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
    void testDtoToEntityNull() throws InvalidAddressException {
        assertNull(addressMapper.dtoToEntity(null));
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
        assertThrows(InvalidAddressException.class, () -> addressMapper.dtoToEntity(dto));
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
        assertThrows(InvalidAddressException.class, () -> addressMapper.dtoToEntity(dto));
    }

    @Test
    void testDtosToEntities() throws InvalidAddressException {
        // Arrange
        AddressDTO dto1 = new AddressDTO();
        dto1.setId(1L);
        dto1.setStreet("Test Street");
        dto1.setHouseNumber("123");
        dto1.setBusNumber("1A");
        dto1.setPostalCode("1234");
        dto1.setCity("Test City");
        dto1.setProvince("Test Province");
        dto1.setCountry("Belgium");

        AddressDTO dto2 = new AddressDTO();
        dto2.setId(2L);
        dto2.setStreet("Test Street 2");
        dto2.setHouseNumber("456");
        dto2.setBusNumber("2B");
        dto2.setPostalCode("5678");
        dto2.setCity("Test City 2");
        dto2.setProvince("Test Province 2");
        dto2.setCountry("Belgium");

        List<AddressDTO> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);

        // Act
        List<Address> addresses = addressMapper.dtosToEntities(dtos);

        // Assert
        assertNotNull(addresses);
        assertEquals(2, addresses.size());
        assertEquals(dto1.getId(), addresses.get(0).getId());
        assertEquals(dto1.getStreet(), addresses.get(0).getStreet());
        assertEquals(dto1.getHouseNumber(), addresses.get(0).getHouseNumber());
        assertEquals(dto1.getBusNumber(), addresses.get(0).getBusNumber());
        assertEquals(dto1.getPostalCode(), addresses.get(0).getPostalCode());
        assertEquals(dto1.getCity(), addresses.get(0).getCity());
        assertEquals(dto1.getProvince(), addresses.get(0).getProvince());
        assertEquals(dto1.getCountry(), addresses.get(0).getCountry());

        assertEquals(dto2.getId(), addresses.get(1).getId());
        assertEquals(dto2.getStreet(), addresses.get(1).getStreet());
        assertEquals(dto2.getHouseNumber(), addresses.get(1).getHouseNumber());
        assertEquals(dto2.getBusNumber(), addresses.get(1).getBusNumber());
        assertEquals(dto2.getPostalCode(), addresses.get(1).getPostalCode());
        assertEquals(dto2.getCity(), addresses.get(1).getCity());
        assertEquals(dto2.getProvince(), addresses.get(1).getProvince());
        assertEquals(dto2.getCountry(), addresses.get(1).getCountry());
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
        AddressDTO dto = addressMapper.entityToDto(address);

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

    @Test
    void testEntityToDtoNull() {
        assertNull(addressMapper.entityToDto(null));
    }

    @Test
    void testEntitiesToDtos() throws InvalidAddressException {
        // Arrange
        Address address1 = new Address();
        address1.setId(1L);
        address1.setStreet("Test Street");
        address1.setHouseNumber("123");
        address1.setBusNumber("1A");
        address1.setPostalCode("1234");
        address1.setCity("Test City");
        address1.setProvince("Test Province");
        address1.setCountry("Belgium");

        Address address2 = new Address();
        address2.setId(2L);
        address2.setStreet("Test Street 2");
        address2.setHouseNumber("456");
        address2.setBusNumber("2B");
        address2.setPostalCode("5678");
        address2.setCity("Test City 2");
        address2.setProvince("Test Province 2");
        address2.setCountry("Belgium");

        List<Address> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);

        // Act
        List<AddressDTO> dtos = addressMapper.entitiesToDtos(addresses);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(address1.getId(), dtos.get(0).getId());
        assertEquals(address1.getStreet(), dtos.get(0).getStreet());
        assertEquals(address1.getHouseNumber(), dtos.get(0).getHouseNumber());
        assertEquals(address1.getBusNumber(), dtos.get(0).getBusNumber());
        assertEquals(address1.getPostalCode(), dtos.get(0).getPostalCode());
        assertEquals(address1.getCity(), dtos.get(0).getCity());
        assertEquals(address1.getProvince(), dtos.get(0).getProvince());
        assertEquals(address1.getCountry(), dtos.get(0).getCountry());

        assertEquals(address2.getId(), dtos.get(1).getId());
        assertEquals(address2.getStreet(), dtos.get(1).getStreet());
        assertEquals(address2.getHouseNumber(), dtos.get(1).getHouseNumber());
        assertEquals(address2.getBusNumber(), dtos.get(1).getBusNumber());
        assertEquals(address2.getPostalCode(), dtos.get(1).getPostalCode());
        assertEquals(address2.getCity(), dtos.get(1).getCity());
        assertEquals(address2.getProvince(), dtos.get(1).getProvince());
        assertEquals(address2.getCountry(), dtos.get(1).getCountry());
    }
}