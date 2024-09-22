package coigniez.rentapp.view.controllers.property;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.renderer.FormRenderer;

import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.PropertyDTO;
import lombok.Getter;

public class PropertyForm {

    private PropertyDTO property;
    List<String> countries;

    private Form form;
    @Getter
    private FormRenderer formRenderer;

    private StringField nameField;
    private StringField streetField;
    private StringField houseNumberField;
    private StringField busNumberField;
    private StringField postalCodeField;
    private StringField cityField;
    private StringField provinceField;
    private SingleSelectionField<String> countryField;

    public PropertyForm(PropertyDTO property, List<String> countries) {
        this.property = property;
        this.countries = countries;
        createForm();
    }

    private void createForm() {
        // set the values of the form fields
        nameField = Field.ofStringType("")
                .label("Name")
                .required("Please enter a name");
        streetField = Field.ofStringType("")
                .label("Street");
        houseNumberField = Field.ofStringType("")
                .label("House number");
        busNumberField = Field.ofStringType("")
                .label("Bus number");
        postalCodeField = Field.ofStringType("")
                .label("Postal code");
        cityField = Field.ofStringType("")
                .label("City");
        provinceField = Field.ofStringType("")
                .label("Province");
        countryField = Field.ofSingleSelectionType(countries, -1)
                .label("Country");

        // Create the form
        form = Form.of(
                new Group[]{
                    Group.of(
                            nameField,
                            streetField,
                            houseNumberField,
                            busNumberField,
                            postalCodeField,
                            cityField,
                            provinceField,
                            countryField)
                })
                .title("Property Form");

        // Render the form
        formRenderer = new FormRenderer(form);
        formRenderer.setMinWidth(800);
    }

    /**
     * Set the form fields to the values of the property
     */
    private void setForm() {
        // Get the values of the form fields
        String name = Objects.requireNonNullElse(property.getName(), "");
        String street = (property.getAddress() == null || property.getAddress().getStreet() == null) ? ""
                : property.getAddress().getStreet();
        String houseNumber = (property.getAddress() == null || property.getAddress().getHouseNumber() == null)
                ? ""
                : property.getAddress().getHouseNumber();
        String busNumber = (property.getAddress() == null || property.getAddress().getBusNumber() == null) ? ""
                : property.getAddress().getBusNumber();
        String postalCode = (property.getAddress() == null || property.getAddress().getPostalCode() == null)
                ? ""
                : property.getAddress().getPostalCode();
        String city = (property.getAddress() == null || property.getAddress().getCity() == null) ? ""
                : property.getAddress().getCity();
        String province = (property.getAddress() == null || property.getAddress().getProvince() == null) ? ""
                : property.getAddress().getProvince();
        // Get the index of the country in the list if the property has a country set
        int index = (property.getAddress() == null) ? -1
                : countries.indexOf(property.getAddress().getCountry());

        // Set the values of the form fields
        nameField.userInputProperty().setValue(name);
        streetField.userInputProperty().setValue(street);
        houseNumberField.userInputProperty().setValue(houseNumber);
        busNumberField.userInputProperty().setValue(busNumber);
        postalCodeField.userInputProperty().setValue(postalCode);
        cityField.userInputProperty().setValue(city);
        provinceField.userInputProperty().setValue(province);
        countryField.select(index);
    }

    /**
     * Get the PropertyDTO from the form fields
     */
    public PropertyDTO getProperty() {
        // Get the values from the form fields
        String name = nullIfEmpty(nameField.getValue());
        String street = nullIfEmpty(streetField.getValue());
        String houseNumber = nullIfEmpty(houseNumberField.getValue());
        String busNumber = nullIfEmpty(busNumberField.getValue());
        String postalCode = nullIfEmpty(postalCodeField.getValue());
        String city = nullIfEmpty(cityField.getValue());
        String province = nullIfEmpty(provinceField.getValue());
        String country = nullIfEmpty(countryField.getSelection());

        // Address is null if all fields are empty
        AddressDTO address = Arrays.asList(street, houseNumber, busNumber, postalCode, city, province, country)
                .stream().allMatch(Objects::isNull)
                ? null
                : new AddressDTO(null, street, houseNumber, busNumber, postalCode, city, province, country);

        // Set the attributes of the property
        property.setName(name);
        property.setAddress(address);

        return property;
    }

    /**
     * Set the PropertyDTO and update the form fields
     */
    public void setProperty(PropertyDTO property) {
        this.property = property;
        setForm();
    }

    /**
     * Return null if the value is empty or null
     */
    private String nullIfEmpty(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }
}