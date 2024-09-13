package coigniez.rentapp.model.property.tag;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Tags.
 */
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper = new TagMapper();

    /**
     * Constructor for TagService.
     *
     * @param tagRepository The repository for Tag.
     */
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Save a Tag.
     *
     * @param tag The TagDTO to save.
     * @return The saved TagDTO.
     */
    public TagDTO saveTag(TagDTO tag) {
        Tag savedTag = tagRepository.save(tagMapper.dtoToEntity(tag));
        return tagMapper.entityToDto(savedTag);
    }

    /**
     * Find a Tag by id.
     *
     * @param id The id of the Tag to find.
     * @return The found TagDTO.
     */
    public Optional<TagDTO> findTagByName(String name) {
        if (name == null) {
            return Optional.empty();
        } else {
            return tagRepository.findById(name).map(tagMapper::entityToDto);
        }
    }

    /**
     * Find all Tags.
     *
     * @return A list of all TagDTOs.
     */
    public List<TagDTO> findAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tagMapper.entitiesToDtos(tags);
    }

    /**
     * Update a Tag.
     *
     * @param tag The TagDTO to update.
     * @return The updated TagDTO.
     */
    public TagDTO updateTag(TagDTO tag) {
        Tag updatedTag = tagRepository.save(tagMapper.dtoToEntity(tag));
        return tagMapper.entityToDto(updatedTag);
    }

    /**
     * Delete a Tag from the database.
     *
     * @param id The id of the Tag to delete.
     */
    public void deleteTag(String name) {
        if (name != null) {
            tagRepository.deleteById(name);
        }
    }

    /**
     * Delete a Tag from the database.
     *
     * @param tag The TagDTO to delete.
     */
    public void deleteTag(TagDTO tag) {
        tagRepository.delete(tagMapper.dtoToEntity(tag));
    }
}