package coigniez.rentapp.model.address;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    @NotEmpty
    @Max(200)
    private String street;
    @NotEmpty
    @Max(10)
    private String houseNumber;
    @Max(10)
    private String busNumber;
    @NotEmpty
    @Max(10)
    private String postalCode;
    @NotEmpty
    @Max(100)
    private String city;
    @Max(100)
    private String province;
    @NotEmpty
    private String country;

}