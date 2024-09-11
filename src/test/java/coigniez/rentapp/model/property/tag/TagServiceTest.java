package coigniez.rentapp.model.property.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    private TagRepository $lowercase_entityRepository;
    private TagService $lowercase_entityService;

    @BeforeEach
    void setUp() {
        $lowercase_entityRepository = Mockito.mock(TagRepository.class);
        $lowercase_entityService = new TagService($lowercase_entityRepository);
    }

    @Test
    void testSaveTag() {
        // Arrange
        TagDTO $lowercase_entity = new TagDTO();
        Tag $lowercase_entityEntity = new Tag();
        when($lowercase_entityRepository.save(any(Tag.class))).thenReturn($lowercase_entityEntity);

        // Act
        TagDTO savedTag = $lowercase_entityService.saveTag($lowercase_entity);

        // Assert
        assertEquals($lowercase_entity, savedTag);
        verify($lowercase_entityRepository, times(1)).save($lowercase_entityEntity);
    }

    @Test
    void testFindTagById() {
        // Arrange
        Tag $lowercase_entity = new Tag();
        $lowercase_entity.setId(1L);
        when($lowercase_entityRepository.findById(1L)).thenReturn(Optional.of($lowercase_entity));

        // Act
        Optional<TagDTO> foundTag = $lowercase_entityService.findTagById(1L);

        // Assert
        assertEquals($lowercase_entity.getId(), foundTag.get().getId());
        verify($lowercase_entityRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllTags() {
        // Arrange
        Tag $lowercase_entity1 = new Tag();
        Tag $lowercase_entity2 = new Tag();
        when($lowercase_entityRepository.findAll()).thenReturn(Arrays.asList($lowercase_entity1, $lowercase_entity2));

        // Act
        List<TagDTO> $lowercase_entitys = $lowercase_entityService.findAllTags();

        // Assert
        assertEquals(2, $lowercase_entitys.size());
        verify($lowercase_entityRepository, times(1)).findAll();
    }

    @Test
    void testUpdateTag() {
        // Arrange
        TagDTO $lowercase_entity = new TagDTO();
        Tag $lowercase_entityEntity = new Tag();
        when($lowercase_entityRepository.save(any(Tag.class))).thenReturn($lowercase_entityEntity);

        // Act
        TagDTO updatedTag = $lowercase_entityService.updateTag($lowercase_entity);

        // Assert
        assertEquals($lowercase_entity, updatedTag);
        verify($lowercase_entityRepository, times(1)).save($lowercase_entityEntity);
    }

    @Test
    void testDeleteTagById() {
        // Arrange
        Long id = 1L;

        // Act
        $lowercase_entityService.deleteTag(id);

        // Assert
        verify($lowercase_entityRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTagByTag() {
        // Arrange
        TagDTO $lowercase_entity = new TagDTO();
        Tag $lowercase_entityEntity = new Tag();

        // Act
        $lowercase_entityService.deleteTag($lowercase_entity);

        // Assert
        verify($lowercase_entityRepository, times(1)).delete($lowercase_entityEntity);
    }
}