package coigniez.rentapp.model;

import coigniez.rentapp.model.exceptions.InvalidPostalCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
    }

    @Test
    void testSetAndGetStreet() {
        String street = "Main Street";
        address.setStreet(street);
        assertEquals(street, address.getStreet());
    }

    @Test
    void testSetAndGetHouseNumber() {
        String houseNumber = "123";
        address.setHouseNumber(houseNumber);
        assertEquals(houseNumber, address.getHouseNumber());
    }

    @Test
    void testSetAndGetBusNumber() {
        String busNumber = "1A";
        address.setBusNumber(busNumber);
        assertEquals(busNumber, address.getBusNumber());
    }

    @Test
    void testSetAndGetPostalCode() {
        String postalCode = "1000";
        assertDoesNotThrow(() -> {
            address.setCountry(Country.BELGIUM.getName());
            address.setPostalCode(postalCode);
        });
        assertEquals(postalCode, address.getPostalCode());
    }

    @Test
    void testSetInvalidPostalCode() {
        String postalCode = "invalid";
        assertThrows(InvalidPostalCodeException.class, () -> {
            address.setCountry(Country.BELGIUM.getName());
            address.setPostalCode(postalCode);
        });
    }

    @Test
    void testSetAndGetCity() {
        String city = "Brussels";
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

    @Test
    void testSetAndGetProvince() {
        String province = "Brussels";
        address.setProvince(province);
        assertEquals(province, address.getProvince());
    }

    @Test
    void testSetAndGetCountryEnum() {
        Country country = Country.BELGIUM;
        assertDoesNotThrow(() -> address.setCountry(country));
        assertEquals(country.getName(), address.getCountry());
    }

    @Test
    void testSetAndGetCountryName() {
        Country country = Country.BELGIUM;
        assertDoesNotThrow(() -> address.setCountry("Belgium"));
        assertEquals(country.getName(), address.getCountry());
    }

    @Test
    void testSetAndGetCountryCode() {
        Country country = Country.BELGIUM;
        assertDoesNotThrow(() -> address.setCountry("BE"));
        assertEquals(country.getName(), address.getCountry());
    }
}