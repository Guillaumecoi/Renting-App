package coigniez.rentapp.model.address;

import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String street;

    @Column(nullable = false, length = 10)
    private String houseNumber;

    @Column(nullable = true, length = 10)
    private String busNumber;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = true, length = 100)
    private String province;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;

    /**
     * @return The country name.
     */
    public String getCountry() {
        return country.getName();
    }

    /**
     * Set the postal code and validate it based on the country.
     * @param postalCode The postal code.
     * @throws InvalidPostalCodeException If the postal code is invalid for the country.
     */
    public void setPostalCode(String postalCode) throws InvalidPostalCodeException {
        if (this.country != null) {
            validatePostalCode(postalCode, this.country);
        }
        this.postalCode = postalCode;
    }

    
    /**
     * Set the country by its name.
     * @param country The name or code of the country.
     * @throws InvalidPostalCodeException If the postal code is invalid for this country.
     */
    public void setCountry(String country) throws InvalidPostalCodeException {
        setCountry(Country.get(country));
    }

    /**
     * Set the country by its enum.
     * @param country The country enum.
     * @throws InvalidPostalCodeException If the postal code is invalid for this country.
     */
    public void setCountry(Country country) throws InvalidPostalCodeException {
        if (this.postalCode != null) {
            validatePostalCode(this.postalCode, country);
        }
        this.country = country;
    }

    /**
     * Validates the postal code of the country based on the postal code regex.
     * @throws InvalidPostalCodeException If the postal code is invalid.
     */
    private void validatePostalCode(String postalCode, Country country) throws InvalidPostalCodeException {
        country.validatePostalCode(postalCode);
    }
    
}