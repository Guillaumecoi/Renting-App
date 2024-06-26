package coigniez.rentapp.model.address;

import org.junit.jupiter.api.Test;

import coigniez.rentapp.exceptions.InvalidAddressException;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {
    @Test
    void testGetCode() {
        assertEquals("BE", Country.BELGIUM.getCode());
        assertEquals("FR", Country.FRANCE.getCode());
        assertEquals("DE", Country.GERMANY.getCode());
        assertEquals("NL", Country.NETHERLANDS.getCode());
        assertEquals("UK", Country.UNITED_KINGDOM.getCode());
    }

    @Test
    void testGetName() {
        assertEquals("Belgium", Country.BELGIUM.getName());
        assertEquals("France", Country.FRANCE.getName());
        assertEquals("Germany", Country.GERMANY.getName());
        assertEquals("Netherlands", Country.NETHERLANDS.getName());
        assertEquals("United Kingdom", Country.UNITED_KINGDOM.getName());
    }

    @Test
    void testValues() {
        Country[] expected = { Country.BELGIUM, Country.FRANCE, Country.GERMANY, Country.NETHERLANDS,
                Country.UNITED_KINGDOM };
        assertArrayEquals(expected, Country.values());
    }

    @Test
    void testGetNames() {
        String[] expected = { "Belgium", "France", "Germany", "Netherlands", "United Kingdom" };
        assertArrayEquals(expected, Country.getNames());
    }

    @Test
    void testGetCodes() {
        String[] expected = { "BE", "FR", "DE", "NL", "UK" };
        assertArrayEquals(expected, Country.getCodes());
    }

    @Test
    void testGet() throws InvalidAddressException {
        assertEquals(Country.BELGIUM, Country.get("BE"));
        assertEquals(Country.FRANCE, Country.get("France"));
        assertEquals(Country.GERMANY, Country.get("DE"));
        assertEquals(Country.NETHERLANDS, Country.get("Netherlands"));
        assertEquals(Country.UNITED_KINGDOM, Country.get("UK"));

        assertThrows(InvalidAddressException.class, () -> Country.get("InvalidCountry"));
    }

    @Test
    void testValidatePostalCodeBelgium() {
        assertDoesNotThrow(() -> Country.BELGIUM.validatePostalCode("1000"));
        assertThrows(InvalidAddressException.class, () -> Country.BELGIUM.validatePostalCode("10000"));
        assertThrows(InvalidAddressException.class, () -> Country.BELGIUM.validatePostalCode("abc"));
    }

    @Test
    void testValidatePostalCodeNetherlands() {
        assertDoesNotThrow(() -> Country.NETHERLANDS.validatePostalCode("1234 AB"));
        assertThrows(InvalidAddressException.class, () -> Country.NETHERLANDS.validatePostalCode("1234"));
        assertThrows(InvalidAddressException.class, () -> Country.NETHERLANDS.validatePostalCode("1234 ABC"));
    }
}
