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
import coigniez.rentapp.model.property.tag.Tag;
import coigniez.rentapp.model.property.tag.TagDTO;

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
        Set<Tag> tags = Set.of(new Tag("tag1"), new Tag("tag2"));
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
        assertTrue(dto.getTags().stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(dto.getTags().stream().anyMatch(tag -> tag.getName().equals("tag2")));
    }

    @Test
    void testEntityToDtoWithNullAddress() {
        // Arrange
        Property entity = new Property();
        entity.setId(1L);
        entity.setName("Test Property");
        entity.setTags(Set.of(new Tag("tag1"), new Tag("tag2")));

        // Act
        PropertyDTO dto = propertyMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertNull(dto.getAddress());
        assertEquals(entity.getTags().size(), dto.getTags().size());
        assertTrue(dto.getTags().stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(dto.getTags().stream().anyMatch(tag -> tag.getName().equals("tag2")));
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
    void testEntityToDtoWithParent() {
        // Arrange
        Property parent = new Property();
        parent.setId(1L);
        parent.setName("Parent Property");
        Property entity = new Property();
        entity.setId(2L);
        entity.setName("Child Property");
        entity.setParent(parent);

        // Act
        PropertyDTO dto = propertyMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertNotNull(dto.getParent());
        assertEquals(parent.getId(), dto.getParent().getId());
        assertEquals(parent.getName(), dto.getParent().getName());
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
        dto.setTags(Set.of(new TagDTO("tag1"), new TagDTO("tag2")));

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
        assertTrue(entity.getTags().stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(entity.getTags().stream().anyMatch(tag -> tag.getName().equals("tag2")));
    }

    @Test
    void testDtoToEntityWithNullAddress() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Test Property");
        dto.setTags(Set.of(new TagDTO("tag1"), new TagDTO("tag2")));

        // Act
        Property entity = propertyMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertNull(entity.getAddress());
        assertEquals(dto.getTags().size(), entity.getTags().size());
        assertTrue(entity.getTags().stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(entity.getTags().stream().anyMatch(tag -> tag.getName().equals("tag2")));
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
    void testDtoToEntityWithParent() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = new PropertyDTO();
        dto.setId(1L);
        dto.setName("Child Property");
        // set ParentDTO for dto
        PropertyDTO parentDto = new PropertyDTO();
        parentDto.setId(2L);
        parentDto.setName("Parent Property");
        dto.setParent(parentDto);

        // Act
        Property entity = propertyMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertNotNull(entity.getParent());
        assertEquals(parentDto.getId(), entity.getParent().getId());
        assertEquals(parentDto.getName(), entity.getParent().getName());
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
        Exception exception = assertThrows(InvalidAddressException.class, () -> propertyMapper.toEntity(dto));

        assertEquals("Invalid country code or name: Invalid Country", exception.getMessage());
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
        Exception exception = assertThrows(InvalidAddressException.class, () -> propertyMapper.toEntity(dto));

        assertEquals("Invalid postal code for Belgium.", exception.getMessage());
    }
}
