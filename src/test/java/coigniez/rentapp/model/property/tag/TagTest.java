package coigniez.rentapp.model.property.tag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {
    @Test
    public void testDummy() {
        // Arrange
        Tag $lowercase_entity = new Tag();
        $lowercase_entity.setName("Test Tag");

        // Act
        String name = $lowercase_entity.getName();

        // Assert
        assertEquals("Test Tag", name);
    }
}