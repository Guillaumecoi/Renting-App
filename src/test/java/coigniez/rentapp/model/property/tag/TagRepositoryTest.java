package coigniez.rentapp.model.property.tag;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    private Tag tag;

    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setName("Test Tag");
        // set properties here
        tag = tagRepository.saveAndFlush(tag);
    }

    @AfterEach
    public void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    public void testCreateTag() {
        // Arrange
        Tag newTag = new Tag();
        newTag.setName("New Tag");

        // Act
        Tag savedTag = tagRepository.save(newTag);

        // Assert
        assertEquals(newTag.getName(), savedTag.getName());
    }

    @Test
    public void testReadTag() {
        // Act
        Optional<Tag> readTag = tagRepository.findById(tag.getName());

        // Assert
        assertTrue(readTag.isPresent());
    }

    @Test
    public void testDeleteTag() {
        // Arrange
        String name = tag.getName();

        // Act
        tagRepository.delete(tag);

        // Assert
        Optional<Tag> deletedTag = tagRepository.findById(name);
        assertFalse(deletedTag.isPresent());
    }

    @Test
    void testDeleteById() {
        // Arrange
        String name = tag.getName();

        // Act
        tagRepository.deleteById(name);

        // Assert
        Optional<Tag> deletedTag = tagRepository.findById(name);
        assertFalse(deletedTag.isPresent());
    }
}