package coigniez.rentapp.model.property.tag;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import coigniez.rentapp.interfaces.MapperInterface;

@Mapper
public interface TagMapper extends MapperInterface<Tag, TagDTO> {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Override
    Tag toEntity(TagDTO dto);

    @Override
    TagDTO toDto(Tag tag);

    default Set<TagDTO> toDtoSet(Set<Tag> tags) {
        return Optional.ofNullable(tags)
                .map(t -> t.stream().map(this::toDto).collect(Collectors.toSet()))
                .orElse(null);
    }

    default Set<Tag> toEntitySet(Set<TagDTO> dtos) {
        return Optional.ofNullable(dtos)
                .map(t -> t.stream().map(this::toEntity).collect(Collectors.toSet()))
                .orElse(null);
    }
}
