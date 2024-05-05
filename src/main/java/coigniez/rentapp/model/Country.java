package coigniez.rentapp.model;

import java.util.Arrays;

public enum Country {
    BELGIUM("BE") {
        @Override
        public void validatePostalCode(String postalCode) {
            if (postalCode.length() != 4 || !postalCode.matches("\\d+")) {
                throw new IllegalArgumentException("Invalid postal code for Belgium. It should be 4 digits.");
            }
        }
    },
    FRANCE("FR"),
    GERMANY("DE"),
    NETHERLANDS("NL") {
        @Override
        public void validatePostalCode(String postalCode) {
            if (!postalCode.matches("\\d{4} [A-Z]{2}")) {
                throw new IllegalArgumentException("Invalid postal code for Netherlands. It should be in the format 'NNNN AA'.");
            }
        }
    },
    UNITED_KINGDOM("UK");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        // convert enum name to lowercase and replace underscores with spaces
        String name = name().toLowerCase().replace('_', ' ');
        // split the name into an array of words
        String[] words = name.split("\\s+");

        // create a StringBuilder to build the name with spaces and uppercase first letters
        StringBuilder nameWithSpaces = new StringBuilder();
        for (String word : words) {
            nameWithSpaces.append(
                word.substring(0, 1).toUpperCase())  // set first letter to uppercase
                .append(word.substring(1))  // append the rest of the word
                .append(" ");  // append a space
        }
        return nameWithSpaces.toString().trim(); // remove the trailing space
    }

    /**
     * @return an array of country names with spaces and uppercase first letters
     */
    public static String[] getNames() {
        return Arrays.stream(Country.values())
                     .map(Country::getName)
                     .toArray(String[]::new);
    }

    public void validatePostalCode(String postalCode) {
        // Default implementation does nothing.
        // Override this method in each enum value to provide country-specific validation.
    }
}