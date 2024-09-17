package coigniez.rentapp.model.property;

import java.util.Set;

import coigniez.rentapp.model.address.AddressDTO;
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
    private String name;
    private AddressDTO address;
    private Set<String> tags;
}
