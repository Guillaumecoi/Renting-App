package coigniez.rentapp.view.controllers.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.renderer.FormRenderer;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.address.Country;
import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.model.property.PropertyService;
import coigniez.rentapp.model.property.tag.TagDTO;
import coigniez.rentapp.view.controllers.MainController;
import coigniez.rentapp.view.controllers.util.TagManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/property/PropertyForm.fxml")
public class PropertyFormController {

    @Autowired
    private PropertyService propertyService;

    @Setter
    PropertyDTO property;

    @Setter
    private MainController mainController;

    @FXML
    private VBox formField;

    private Form form;
    private FormRenderer formRenderer;
    private List<String> selectedTags;

    @FXML
    public void initialize() {
        property = new PropertyDTO();
        selectedTags = new ArrayList<>();
        TagManager tagManager = new TagManager(propertyService.findAllTags().stream().map(TagDTO::getName).toList());
        setForm(property);

        // Add a submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> addProperty());

        // Add the form and the submit button to the form field
        formField.getChildren().add(formRenderer);
        formField.getChildren().add(tagManager.getPane());
        formField.getChildren().add(submitButton);
        formField.setSpacing(10);
    }

    @SuppressWarnings("rawtypes")
    private void addProperty() {
        // Get the values from the form fields
        String name = nullIfEmpty(((StringField) form.getFields().get(0)).getValue());
        String street = nullIfEmpty(((StringField) form.getFields().get(1)).getValue());
        String houseNumber = nullIfEmpty(((StringField) form.getFields().get(2)).getValue());
        String busNumber = nullIfEmpty(((StringField) form.getFields().get(3)).getValue());
        String postalCode = nullIfEmpty(((StringField) form.getFields().get(4)).getValue());
        String city = nullIfEmpty(((StringField) form.getFields().get(5)).getValue());
        String province = nullIfEmpty(((StringField) form.getFields().get(6)).getValue());
        String country = nullIfEmpty((String) (((SingleSelectionField) form.getFields().get(7)).getSelection()));

        // Address is null if all fields are empty
        AddressDTO address = Arrays.asList(street, houseNumber, busNumber, postalCode, city, province, country)
                .stream().allMatch(Objects::isNull)
                ? null
                : new AddressDTO(null, street, houseNumber, busNumber, postalCode, city, province, country);

        // Set the attributes of the property
        property.setName(name);
        property.setAddress(address);
        property.setTags(selectedTags.stream().map(tag -> new TagDTO(tag))
                .collect(HashSet::new, HashSet::add, HashSet::addAll));

        System.out.println("The following property is submitted:" + property);

        try {
            property = propertyService.saveProperty(property);
            showSuccessAlert(property);

        } catch (InvalidAddressException | DataIntegrityViolationException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to add property");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to show success alert with custom buttons
    private void showSuccessAlert(PropertyDTO property) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Property Submitted Successfully");
        alert.setContentText("The property: \n" + property + "\nhas been submitted successfully. What would you like to do next?");

        ButtonType buttonTypeReturn = new ButtonType("Return");
        ButtonType buttonTypeAddAnother = new ButtonType("Add Another");

        alert.getButtonTypes().setAll(buttonTypeReturn, buttonTypeAddAnother);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeReturn) {
            // Handle return action
            mainController.homeScreen();
        } else if (result.isPresent() && result.get() == buttonTypeAddAnother) {
            // clear the form
            formField.getChildren().clear();
            initialize();
        }
    }

    private void setForm(PropertyDTO property) {
        List<String> countries = List.of(Country.getNames());

        // Set the values of the form fields
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

        // Create the form
        form = Form.of(
                Group.of(
                        Field.ofStringType(name)
                                .label("Name")
                                .required("Please enter a name"),
                        Field.ofStringType(street)
                                .label("Street"),
                        Field.ofStringType(houseNumber)
                                .label("House number"),
                        Field.ofStringType(busNumber)
                                .label("Bus number"),
                        Field.ofStringType(postalCode)
                                .label("Postal code"),
                        Field.ofStringType(city)
                                .label("City"),
                        Field.ofStringType(province)
                                .label("Province"),
                        Field.ofSingleSelectionType(countries, index)
                                .label("Country")))
                .title("Property Form");

        // Render the form
        formRenderer = new FormRenderer(form);
        formRenderer.setMinWidth(800);
    }

    /**
     * Return null if the value is empty or null
     */
    private String nullIfEmpty(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }
}
