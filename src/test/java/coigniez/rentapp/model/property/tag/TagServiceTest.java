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

    private TagRepository tagRepository;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository);
    }

    @Test
    void testSaveTag() {
        // Arrange
        TagDTO tag = new TagDTO();
        Tag tagEntity = new Tag();
        when(tagRepository.save(any(Tag.class))).thenReturn(tagEntity);

        // Act
        TagDTO savedTag = tagService.saveTag(tag);

        // Assert
        assertEquals(tag, savedTag);
        verify(tagRepository, times(1)).save(tagEntity);
    }

    @Test
    void testFindTagById() {
        // Arrange
        Tag tag = new Tag();
        tag.setName("1");
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Act
        Optional<TagDTO> foundTag = tagService.findTagByName("1");

        // Assert
        assertEquals(tag.getName(), foundTag.get().getName());
        verify(tagRepository, times(1)).findById("1");
    }

    @Test
    void testFindAllTags() {
        // Arrange
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        // Act
        List<TagDTO> $lowercase_entitys = tagService.findAllTags();

        // Assert
        assertEquals(2, $lowercase_entitys.size());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTagById() {
        // Arrange
        String id = "1";

        // Act
        tagService.deleteTag(id);

        // Assert
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTagByTag() {
        // Arrange
        TagDTO tag = new TagDTO();
        Tag tagEntity = new Tag();

        // Act
        tagService.deleteTag(tag);

        // Assert
        verify(tagRepository, times(1)).delete(tagEntity);
    }
}