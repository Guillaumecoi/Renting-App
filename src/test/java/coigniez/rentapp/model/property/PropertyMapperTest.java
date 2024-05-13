package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;

import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.exceptions.InvalidAddressException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class PropertyMapperTest {

    private PropertyMapper propertyMapper;

    @BeforeEach
    public void setup() {
        propertyMapper = new PropertyMapper();
    }

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

        // Act
        PropertyDTO dto = propertyMapper.entityToDto(entity);

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
    }

    @Test
    void testEntityToDtoWithNull() {
        // Arrange
        Property entity = null;

        // Act
        PropertyDTO dto = propertyMapper.entityToDto(entity);

        // Assert
        assertNull(dto);
    }

    @Test
    void testEntitiesToDtos() throws InvalidAddressException {
        // Arrange
        Property entity1 = new Property();
        entity1.setId(1L);
        entity1.setName("Test Property 1");
        // set Address for entity1
        Address address1 = new Address();
        address1.setId(1L);
        address1.setStreet("Test Street 1");
        address1.setHouseNumber("123");
        address1.setBusNumber("1A");
        address1.setPostalCode("1234");
        address1.setCity("Test City 1");
        address1.setProvince("Test Province 1");
        address1.setCountry("Belgium");
        entity1.setAddress(address1);

        Property entity2 = new Property();
        entity2.setId(2L);
        entity2.setName("Test Property 2");
        // set Address for entity2
        Address address2 = new Address();
        address2.setId(2L);
        address2.setStreet("Test Street 2");
        address2.setHouseNumber("456");
        address2.setBusNumber("2B");
        address2.setPostalCode("5678");
        address2.setCity("Test City 2");
        address2.setProvince("Test Province 2");
        address2.setCountry("Belgium");
        entity2.setAddress(address2);

        // Act
        PropertyDTO dto1 = propertyMapper.entityToDto(entity1);
        PropertyDTO dto2 = propertyMapper.entityToDto(entity2);

        // Assert
        assertNotNull(dto1);
        assertEquals(entity1.getId(), dto1.getId());
        assertEquals(entity1.getName(), dto1.getName());
        // assert AddressDTO for entity1
        assertEquals(entity1.getAddress().getId(), dto1.getAddress().getId());
        assertEquals(entity1.getAddress().getStreet(), dto1.getAddress().getStreet());
        assertEquals(entity1.getAddress().getHouseNumber(), dto1.getAddress().getHouseNumber());
        assertEquals(entity1.getAddress().getBusNumber(), dto1.getAddress().getBusNumber());
        assertEquals(entity1.getAddress().getPostalCode(), dto1.getAddress().getPostalCode());
        assertEquals(entity1.getAddress().getCity(), dto1.getAddress().getCity());
        assertEquals(entity1.getAddress().getProvince(), dto1.getAddress().getProvince());
        assertEquals(entity1.getAddress().getCountry(), dto1.getAddress().getCountry());

        assertNotNull(dto2);
        assertEquals(entity2.getId(), dto2.getId());
        assertEquals(entity2.getName(), dto2.getName());
        // assert AddressDTO for entity2
        assertEquals(entity2.getAddress().getId(), dto2.getAddress().getId());
        assertEquals(entity2.getAddress().getStreet(), dto2.getAddress().getStreet());
        assertEquals(entity2.getAddress().getHouseNumber(), dto2.getAddress().getHouseNumber());
        assertEquals(entity2.getAddress().getBusNumber(), dto2.getAddress().getBusNumber());
        assertEquals(entity2.getAddress().getPostalCode(), dto2.getAddress().getPostalCode());
        assertEquals(entity2.getAddress().getCity(), dto2.getAddress().getCity());
        assertEquals(entity2.getAddress().getProvince(), dto2.getAddress().getProvince());
        assertEquals(entity2.getAddress().getCountry(), dto2.getAddress().getCountry());
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

        // Act
        Property entity = propertyMapper.dtoToEntity(dto);

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
    }

    @Test
    void testDtoToEntityWithNull() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto = null;

        // Act
        Property entity = propertyMapper.dtoToEntity(dto);

        // Assert
        assertNull(entity);
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
        assertThrows(InvalidAddressException.class, () -> propertyMapper.dtoToEntity(dto));  
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
        assertThrows(InvalidAddressException.class, () -> propertyMapper.dtoToEntity(dto));  
    }

    @Test
    void testDtosToEntities() throws InvalidAddressException {
        // Arrange
        PropertyDTO dto1 = new PropertyDTO();
        dto1.setId(1L);
        dto1.setName("Test Property 1");
        // set AddressDTO for dto1
        AddressDTO AddressDto1 = new AddressDTO();
        AddressDto1.setId(1L);
        AddressDto1.setStreet("Test Street 1");
        AddressDto1.setHouseNumber("123");
        AddressDto1.setBusNumber("1A");
        AddressDto1.setPostalCode("1234");
        AddressDto1.setCity("Test City 1");
        AddressDto1.setProvince("Test Province 1");
        AddressDto1.setCountry("Belgium");
        dto1.setAddress(AddressDto1);

        PropertyDTO dto2 = new PropertyDTO();
        dto2.setId(2L);
        dto2.setName("Test Property 2");
        // set AddressDTO for dto2
        AddressDTO AddressDto2 = new AddressDTO();
        AddressDto2.setId(2L);
        AddressDto2.setStreet("Test Street 2");
        AddressDto2.setHouseNumber("456");
        AddressDto2.setBusNumber("2B");
        AddressDto2.setPostalCode("5678");
        AddressDto2.setCity("Test City 2");
        AddressDto2.setProvince("Test Province 2");
        AddressDto2.setCountry("Belgium");
        dto2.setAddress(AddressDto2);

        // Act
        Property entity1 = propertyMapper.dtoToEntity(dto1);
        Property entity2 = propertyMapper.dtoToEntity(dto2);

        // Assert
        assertNotNull(entity1);
        assertEquals(dto1.getId(), entity1.getId());
        assertEquals(dto1.getName(), entity1.getName());
        // assert Address entity for dto1
        assertEquals(dto1.getAddress().getId(), entity1.getAddress().getId());
        assertEquals(dto1.getAddress().getStreet(), entity1.getAddress().getStreet());
        assertEquals(dto1.getAddress().getHouseNumber(), entity1.getAddress().getHouseNumber());
        assertEquals(dto1.getAddress().getBusNumber(), entity1.getAddress().getBusNumber());
        assertEquals(dto1.getAddress().getPostalCode(), entity1.getAddress().getPostalCode());
        assertEquals(dto1.getAddress().getCity(), entity1.getAddress().getCity());
        assertEquals(dto1.getAddress().getProvince(), entity1.getAddress().getProvince());
        assertEquals(dto1.getAddress().getCountry(), entity1.getAddress().getCountry());

        // assert entity2
        assertNotNull(entity2);
        assertEquals(dto2.getId(), entity2.getId());
        assertEquals(dto2.getName(), entity2.getName());
        // assert Address entity for dto2
        assertEquals(dto2.getAddress().getId(), entity2.getAddress().getId());
        assertEquals(dto2.getAddress().getStreet(), entity2.getAddress().getStreet());
        assertEquals(dto2.getAddress().getHouseNumber(), entity2.getAddress().getHouseNumber());
        assertEquals(dto2.getAddress().getBusNumber(), entity2.getAddress().getBusNumber());
        assertEquals(dto2.getAddress().getPostalCode(), entity2.getAddress().getPostalCode());
        assertEquals(dto2.getAddress().getCity(), entity2.getAddress().getCity());
        assertEquals(dto2.getAddress().getProvince(), entity2.getAddress().getProvince());
        assertEquals(dto2.getAddress().getCountry(), entity2.getAddress().getCountry());
    }

}
