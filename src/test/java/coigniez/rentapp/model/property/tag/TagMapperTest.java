package coigniez.rentapp.model.property.tag;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagMapperTest {

    private final TagMapper tagMapper = new TagMapper();

    @Test
    void entityToDto() {
        // Arrange
        Tag entity = new Tag();
        entity.setId(1L);
        entity.setName("name");

        // Act
        TagDTO dto = tagMapper.entityToDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    void entityToDtoNull() {
        assertNull(tagMapper.entityToDto(null));
    }

    @Test
    void testEntitiesToDtos() {
        // Arrange
        Tag entity1 = new Tag();
        entity1.setId(1L);
        entity1.setName("name1");
        Tag entity2 = new Tag();
        entity2.setId(2L);
        entity2.setName("name2");

        List<Tag> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);

        // Act
        List<TagDTO> dtos = tagMapper.entitiesToDtos(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entity1.getId(), dtos.get(0).getId());
        assertEquals(entity1.getName(), dtos.get(0).getName());
        assertEquals(entity2.getId(), dtos.get(1).getId());
        assertEquals(entity2.getName(), dtos.get(1).getName());
    }

    @Test
    void dtoToEntity() {
        // Arrange
        TagDTO dto = new TagDTO();
        dto.setId(1L);
        dto.setName("name");

        // Act
        Tag entity = tagMapper.dtoToEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    void dtoToEntityNull() {
        assertNull(tagMapper.dtoToEntity(null));
    }

    @Test
    void testDtosToEntities() {
        // Arrange
        TagDTO dto1 = new TagDTO();
        dto1.setId(1L);
        dto1.setName("name1");
        TagDTO dto2 = new TagDTO();
        dto2.setId(2L);
        dto2.setName("name2");

        List<TagDTO> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);

        // Act
        List<Tag> entities = tagMapper.dtosToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dto1.getId(), entities.get(0).getId());
        assertEquals(dto1.getName(), entities.get(0).getName());
        assertEquals(dto2.getId(), entities.get(1).getId());
        assertEquals(dto2.getName(), entities.get(1).getName());
    }
}