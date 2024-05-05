package coigniez.rentapp.model;

import org.junit.jupiter.api.Test;
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
    void testValues() {
        Country[] expected = {Country.BELGIUM, Country.FRANCE, Country.GERMANY, Country.NETHERLANDS, Country.UNITED_KINGDOM};
        assertArrayEquals(expected, Country.values());
    }

    @Test
    void testGetNames() {
        String[] expected = {"Belgium", "France", "Germany", "Netherlands", "United Kingdom"};
        assertArrayEquals(expected, Country.getNames());
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
    void testValidatePostalCodeBelgium() {
        assertDoesNotThrow(() -> Country.BELGIUM.validatePostalCode("1000"));
        assertThrows(IllegalArgumentException.class, () -> Country.BELGIUM.validatePostalCode("10000"));
        assertThrows(IllegalArgumentException.class, () -> Country.BELGIUM.validatePostalCode("abc"));
    }

    @Test
    void testValidatePostalCodeNetherlands() {
        assertDoesNotThrow(() -> Country.NETHERLANDS.validatePostalCode("1234 AB"));
        assertThrows(IllegalArgumentException.class, () -> Country.NETHERLANDS.validatePostalCode("1234"));
        assertThrows(IllegalArgumentException.class, () -> Country.NETHERLANDS.validatePostalCode("1234 ABC"));
    }
}
