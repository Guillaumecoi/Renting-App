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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        // Add street and house number
        result.append(street).append(" ").append(houseNumber);
        // Add bus number if available
        if (busNumber != null && !busNumber.isEmpty()) {
            result.append(" ").append(busNumber);
        }
        // Add postal code, city, province and country
        result.append(", ").append(postalCode).append(" ").append(city);
        // Only add province if available
        if (province != null && !province.isEmpty()) {
            result.append(", ").append(province);
        }
        result.append(", ").append(country);

        return result.toString();
    }

    /**
     * Create a copy of the address. The copy has the same values as the
     * original but null as id.
     *
     * @return a copy of the address
     */
    public AddressDTO copy() {
        return new AddressDTO(null, street, houseNumber, busNumber, postalCode, city, province, country);
    }
}
