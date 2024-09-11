package coigniez.rentapp.model.property.tag;

import java.util.ArrayList;
import java.util.List;

import coigniez.rentapp.interfaces.Mapper;

public class TagMapper implements Mapper<Tag, TagDTO> {

    public TagDTO entityToDto(Tag entity) {
        if (entity == null) {
            return null;
        }

        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public List<TagDTO> entitiesToDtos(List<Tag> entities) {
        if (entities == null) {
            return null;
        }

        List<TagDTO> dtos = new ArrayList<>();
        for (Tag entity : entities) {
            dtos.add(entityToDto(entity));
        }

        return dtos;
    }

    public Tag dtoToEntity(TagDTO dto) {
        if (dto == null) {
            return null;
        }

        Tag entity = new Tag();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

    public List<Tag> dtosToEntities(List<TagDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Tag> entities = new ArrayList<>();
        for (TagDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }

        return entities;
    }
}