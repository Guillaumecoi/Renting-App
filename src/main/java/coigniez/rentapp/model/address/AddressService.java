package coigniez.rentapp.model.address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    /**
     * Get all countries
     * @return array of country names
     */
    public String[] getCountries() {
        return Country.getNames();
    } 

    /**
     * Get all country codes
     * @return array of country codes
     */
    public String[] getCountryCodes() {
        return Country.getCodes();
    }
}
