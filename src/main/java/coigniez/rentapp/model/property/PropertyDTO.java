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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PropertyDTO {

    private Long id;
    @NotEmpty(message = "The name of the property cannot be empty")
    private String name;
    private AddressDTO address;
    private Set<TagDTO> tags;

    /**
     * Set the tags of the property
     *
     * @param List<String> tags
     */
    public void setTagsFromList(List<String> tags) {
        this.tags = tags.stream().map(TagDTO::new).collect(Collectors.toSet());
    }

}
