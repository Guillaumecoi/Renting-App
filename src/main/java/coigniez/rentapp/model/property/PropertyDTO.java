package coigniez.rentapp.model.property;

import coigniez.rentapp.model.address.AddressDTO;
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

}
