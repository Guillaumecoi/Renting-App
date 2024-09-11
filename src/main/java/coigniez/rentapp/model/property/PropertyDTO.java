package coigniez.rentapp.model.property;

import java.util.Set;

import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.tag.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDTO {
    private Long id;
    private String name;
    private AddressDTO address;
    private Set<TagDTO> tags;
}
