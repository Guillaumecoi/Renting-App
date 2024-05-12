package coigniez.rentapp.model.address;

import java.util.Arrays;

import coigniez.rentapp.model.exceptions.InvalidAddressException;
import lombok.Getter;

/**
 * Enum representing various countries.
 * Each country has a code, a name, and a postal code regular expression for validation.
 * The {@link #get(String)} method can be used to get a country by its code or name.
 * The {@link #validatePostalCode(String)} method can be used to validate a postal code for a country.
 */
@Getter
public enum Country {
    // Enum values
    BELGIUM("BE", "Belgium", "\\d{4}"),
    FRANCE("FR", "France", null),
    GERMANY("DE", "Germany", null),
    NETHERLANDS("NL", "Netherlands", "\\d{4} [A-Z]{2}"),
    UNITED_KINGDOM("UK", "United Kingdom", null);

    // Enum fields
    private final String code;
    private final String name;
    private final String postalCodeRegex;

    // Constructor
    Country(String code, String name, String postalCodeRegex) {
        this.code = code;
        this.name = name;
        this.postalCodeRegex = postalCodeRegex;
    }


    // Methods

    /**
     * Get the country enum by its code or name.
     * @param codeOrName The code or name of the country.
     * @return The country enum.
     * @throws InvalidAddressException If the code or name is not in the enum list.
     */
    public static Country get(String codeOrName) throws InvalidAddressException {
        return Arrays.stream(values())
                .filter(country -> country.code.equals(codeOrName) || country.name.equals(codeOrName))
                .findFirst()
                .orElseThrow(() -> new InvalidAddressException("Invalid country code or name: " + codeOrName));
    }

    /**
     * Get the names of all countries.
     * @return An array of country names.
     */
    public static String[] getNames() {
        return Arrays.stream(values())
                .map(Country::getName)
                .toArray(String[]::new);
    }

    /**
     * Get the codes of all countries.
     * @return An array of country codes.
     */
    public static String[] getCodes() {
        return Arrays.stream(values())
                .map(Country::getCode)
                .toArray(String[]::new);
    }

    /**
     * Validate the postal code of the country based on the postal code regex.
     * @param postalCode The postal code to validate.
     * @throws InvalidAddressException If the postal code is invalid.
     */
    public void validatePostalCode(String postalCode) throws InvalidAddressException {
        if (postalCodeRegex == null) {
            return;
        }
        if (!postalCode.matches(postalCodeRegex)) {
            throw new InvalidAddressException("Invalid postal code for " + name + ".");
        }
    }
}