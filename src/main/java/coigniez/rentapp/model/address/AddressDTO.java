package coigniez.rentapp.model.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String houseNumber;
    private String busNumber;
    private String postalCode;
    private String city;
    private String province;
    private String country;

}