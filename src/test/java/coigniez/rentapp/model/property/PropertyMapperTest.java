package coigniez.rentapp.model.property;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.address.AddressDTO;

public class PropertyMapperTest {

    private final PropertyMapper propertyMapper = PropertyMapper.INSTANCE;

    @Test
    void testEntityToDto() throws InvalidAddressException {
        // Arrange
        Property entity = new Property();
        entity.setId(1L);
        entity.setName("Test Property");
        // set Address for entity
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        entity.setAddress(address);
        // set Tags for entity
        Set<String> tags = Set.of("tag1", "tag2");
        entity.setTags(tags);

        // Act
        PropertyDTO dto = propertyMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        // assert AddressDTO
        assertEquals(entity.getAddress().getId(), dto.getAddress().getId());
        assertEquals(entity.getAddress().getStreet(), dto.getAddress().getStreet());
        assertEquals(entity.getAddress().getHouseNumber(), dto.getAddress().getHouseNumber());
        assertEquals(entity.getAddress().getBusNumber(), dto.getAddress().getBusNumber());
        assertEquals(entity.getAddress().getPostalCode(), dto.getAddress().getPostalCode());
        assertEquals(entity.getAddress().getCity(), dto.getAddress().getCity());
        assertEquals(entity.getAddress().getProvince(), dto.getAddress().getProvince());
        assertEquals(entity.getAddress().getCountry(), dto.getAddress().getCountry());
        // assert Tags
        assertEquals(entity.getTags().size(), dto.getTags().size());
        assertTrue(dto.getTags().contains("tag1"));
        assertTrue(dto.getTags().contains("tag2"));
    }

    @Test
    void testEntityToDtoWithNullAddress() {
        // Arrange
        Property entity = new Property();
        entity.setId(1L);
        entity.setName("Test Property");
        entity.setTags(Set.of("tag1", "tag2"));

        // Act
        PropertyDTO dto = propertyMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertNull(dto.getAddress());
        assertEquals(entity.getTags().size(), dto.getTags().size());
        assertTrue(dto.getTags().contains("tag1"));
        assertTrue(dto.getTags().contains("tag2"));
    }

    @Test
    void testEntityToDtoWithNullTags() throws InvalidAddressException {
        // Arrange
        Property entity = new Property();
        entity.setId(1L);
        entity.setName("Test Property");
        // set Address for entity
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        address.setBusNumber("1A");
        address.setPostalCode("1234");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setCountry("Belgium");
        entity.setAddress(address);

        // Act
        PropertyDTO dto = propertyMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        // assert AddressDTO
        assertEquals(entity.getAddress().getId(), dto.getAddress().getId());
        assertEquals(entity.getAddress().getStreet(), dto.getAddress().getStreet());
        assertEquals(entity.getAddress().getHouseNumber(), dto.getAddress().getHouseNumber());
        assertEquals(entity.getAddress().getBusNumber(), dto.getAddress().getBusNumber());
        assertEquals(entity.getAddress().getPostalCode(), dto.getAddress().getPostalCode());
        assertEquals(entity.getAddress().getCity(), dto.getAddress().getCity());
        assertEquals(entity.getAddress().getProvince(), dto.getAddress().getProvince());
        assertEquals(entity.getAddress().getCountry(), dto.getAddress().getCountry());
        assertNull(dto.getTags());
    }

    @Test
    void testEntityToDtoWithNull() {
        assertNull(propertyMapper.toDto(null));
    }

    @Test
    void testDtoToEntity() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        // set AddressDTO for dto
        AddressDTO AddressDto = new AddressDTO();
        AddressDto.setId(1L);
        AddressDto.setStreet("Test Street");
        AddressDto.setHouseNumber("123");
        AddressDto.setBusNumber("1A");
        AddressDto.setPostalCode("1234");
        AddressDto.setCity("Test City");
        AddressDto.setProvince("Test Province");
        AddressDto.setCountry("Belgium");
        dto.setAddress(AddressDto);
        // set Tags for dto
        dto.setTags(Set.of("tag1", "tag2"));

        // Act
        Property entity = propertyMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        // assert Address entity
        assertEquals(dto.getAddress().getId(), entity.getAddress().getId());
        assertEquals(dto.getAddress().getStreet(), entity.getAddress().getStreet());
        assertEquals(dto.getAddress().getHouseNumber(), entity.getAddress().getHouseNumber());
        assertEquals(dto.getAddress().getBusNumber(), entity.getAddress().getBusNumber());
        assertEquals(dto.getAddress().getPostalCode(), entity.getAddress().getPostalCode());
        assertEquals(dto.getAddress().getCity(), entity.getAddress().getCity());
        assertEquals(dto.getAddress().getProvince(), entity.getAddress().getProvince());
        assertEquals(dto.getAddress().getCountry(), entity.getAddress().getCountry());
        // assert Tags
        assertEquals(dto.getTags().size(), entity.getTags().size());
        assertTrue(entity.getTags().contains("tag1"));
        assertTrue(entity.getTags().contains("tag2"));
    }

    @Test
    void testDtoToEntityWithNullAddress() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        dto.setTags(Set.of("tag1", "tag2"));

        // Act
        Property entity = propertyMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertNull(entity.getAddress());
        assertEquals(dto.getTags().size(), entity.getTags().size());
        assertTrue(entity.getTags().contains("tag1"));
        assertTrue(entity.getTags().contains("tag2"));
    }

    @Test
    void testDtoToEntityWithNullTags() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        // set AddressDTO for dto
        AddressDTO AddressDto = new AddressDTO();
        AddressDto.setId(1L);
        AddressDto.setStreet("Test Street");
        AddressDto.setHouseNumber("123");
        AddressDto.setBusNumber("1A");
        AddressDto.setPostalCode("1234");
        AddressDto.setCity("Test City");
        AddressDto.setProvince("Test Province");
        AddressDto.setCountry("Belgium");
        dto.setAddress(AddressDto);

        // Act
        Property entity = propertyMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        // assert Address entity
        assertEquals(dto.getAddress().getId(), entity.getAddress().getId());
        assertEquals(dto.getAddress().getStreet(), entity.getAddress().getStreet());
        assertEquals(dto.getAddress().getHouseNumber(), entity.getAddress().getHouseNumber());
        assertEquals(dto.getAddress().getBusNumber(), entity.getAddress().getBusNumber());
        assertEquals(dto.getAddress().getPostalCode(), entity.getAddress().getPostalCode());
        assertEquals(dto.getAddress().getCity(), entity.getAddress().getCity());
        assertEquals(dto.getAddress().getProvince(), entity.getAddress().getProvince());
        assertEquals(dto.getAddress().getCountry(), entity.getAddress().getCountry());
        assertNull(entity.getTags());
    }

    @Test
    void testDtoToEntityWithNull() throws InvalidAddressException {
        assertNull(propertyMapper.toEntity(null));
    }

    @Test
    void testDtoToEntityWithInvalidCountry() {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        // set AddressDTO for dto
        AddressDTO AddressDto = new AddressDTO();
        AddressDto.setId(1L);
        AddressDto.setStreet("Test Street");
        AddressDto.setHouseNumber("123");
        AddressDto.setBusNumber("1A");
        AddressDto.setPostalCode("1234");
        AddressDto.setCity("Test City");
        AddressDto.setProvince("Test Province");
        AddressDto.setCountry("Invalid Country");
        dto.setAddress(AddressDto);

        // Act & Assert
        assertThrows(InvalidAddressException.class, () -> propertyMapper.toEntity(dto));
    }

    @Test
    void testDtoToEntityWithInvalidPostalCode() {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        // set AddressDTO for dto
        AddressDTO AddressDto = new AddressDTO();
        AddressDto.setId(1L);
        AddressDto.setStreet("Test Street");
        AddressDto.setHouseNumber("123");
        AddressDto.setBusNumber("1A");
        AddressDto.setPostalCode("Invalid Postal Code");
        AddressDto.setCity("Test City");
        AddressDto.setProvince("Test Province");
        AddressDto.setCountry("Belgium");
        dto.setAddress(AddressDto);

        // Act & Assert
        assertThrows(InvalidAddressException.class, () -> propertyMapper.toEntity(dto));
    }
}
