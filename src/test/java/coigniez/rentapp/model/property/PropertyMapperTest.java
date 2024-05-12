package coigniez.rentapp.model.property;

import org.junit.jupiter.api.Test;

import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyMapperTest {

    @Test
    void testDtoToEntity() throws InvalidPostalCodeException {
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

        Property entity = PropertyMapper.dtoToEntity(dto);

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
    void testEntityToDto() throws InvalidPostalCodeException {
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

        PropertyDTO dto = PropertyMapper.entityToDto(entity);

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
}
