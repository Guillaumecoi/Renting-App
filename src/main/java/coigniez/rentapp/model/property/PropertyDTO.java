package coigniez.rentapp.model.property;

import java.util.Set;

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
}
