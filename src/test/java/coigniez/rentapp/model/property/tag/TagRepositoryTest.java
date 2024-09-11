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
    private TagRepository $lowercase_entityRepository;

    private Tag $lowercase_entity;

    @BeforeEach
    public void setUp() {
        $lowercase_entity = new Tag();
        $lowercase_entity.setName("Test Tag");
        // set properties here
        $lowercase_entity = $lowercase_entityRepository.saveAndFlush($lowercase_entity);
    }

    @AfterEach
    public void tearDown() {
        $lowercase_entityRepository.deleteAll();
    }

    @Test
    public void testCreateTag() {
        // Arrange
        Tag newTag = new Tag();
        newTag.setName("New Tag");

        // Act
        Tag savedTag = $lowercase_entityRepository.save(newTag);

        // Assert
        assertNotNull(savedTag.getId());
        assertEquals(newTag.getName(), savedTag.getName());
    }

    @Test
    public void testReadTag() {
        // Act
        Optional<Tag> readTag = $lowercase_entityRepository.findById($lowercase_entity.getId());

        // Assert
        assertTrue(readTag.isPresent());
    }

    @Test
    public void testUpdateTag() {
        // Arrange
        $lowercase_entity.setName("Updated Tag");

        // Act
        $lowercase_entityRepository.saveAndFlush($lowercase_entity);

        // Assert
        Tag updatedTag = $lowercase_entityRepository.findById($lowercase_entity.getId()).orElse(null);
        assertEquals("Updated Tag", updatedTag.getName());
    }

    @Test
    public void testDeleteTag() {
        // Arrange
        Long id = $lowercase_entity.getId();

        // Act
        $lowercase_entityRepository.delete($lowercase_entity);

        // Assert
        Optional<Tag> deletedTag = $lowercase_entityRepository.findById(id);
        assertFalse(deletedTag.isPresent());
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long id = $lowercase_entity.getId();

        // Act
        $lowercase_entityRepository.deleteById(id);

        // Assert
        Optional<Tag> deletedTag = $lowercase_entityRepository.findById(id);
        assertFalse(deletedTag.isPresent());
    }
}