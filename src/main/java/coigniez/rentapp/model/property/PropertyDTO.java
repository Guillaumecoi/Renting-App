package coigniez.rentapp.model.property;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.tag.TagDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDTO {

    private Long id;
    @NotEmpty(message = "The name of the property cannot be empty")
    private String name;
    private AddressDTO address;
    private Set<TagDTO> tags;
    private PropertyDTO parent;

    /**
     * Set the tags of the property
     *
     * @param List<String> tags
     */
    public void setTagsFromList(List<String> tags) {
        this.tags = tags.stream().map(TagDTO::new).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: ").append(name);
        if (address != null) {
            sb.append(", address:").append(address);
        }
        if (tags != null) {
            sb.append(", tags: ").append(tags.stream().map(TagDTO::getName).collect(Collectors.joining(", ")));
        }
        return sb.toString();
    }

    /**
     * Create a copy of the property.
     *
     * @return a copy of the property
     */
    public PropertyDTO copy() {
        return new PropertyDTO(id, name, address, tags, parent);
    }

}
