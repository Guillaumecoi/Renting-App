package coigniez.rentapp.view.controllers.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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

    @FXML
    private VBox formField;

    private Form form;
    private FormRenderer formRenderer;
    private FlowPane tagContainer;
    private ComboBox<String> tagComboBox;
    private List<String> availableTags;
    private List<String> selectedTags = new ArrayList<>();

    @FXML
    public void initialize() {
        property = new PropertyDTO();
        createTagsPane();
        setForm(property);

        // Add a submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> addProperty());

        // Add the form and the submit button to the form field
        formField.getChildren().add(formRenderer);
        formField.getChildren().add(tagContainer);
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
            propertyService.saveProperty(property);
        } catch (InvalidAddressException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to add property");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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

    private void createTagsPane() {
        // Get the available tags
        availableTags = propertyService.findAllTags().stream().map(TagDTO::getName).toList();

        // Set the attributes for the tag input field
        HBox tagBox = new HBox();
        Label tagInuptLabel = new Label("Tags:");
        Label tagListLabel = new Label("Selected tags:");
        tagComboBox = new ComboBox<>();
        tagComboBox.setTooltip(new Tooltip("Press enter to add the tag"));
        tagComboBox.getItems().addAll(availableTags);
        tagComboBox.setEditable(true);

        // Add the tag to the selected tags list when the user presses enter
        tagComboBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Get the text from the input field
                String tag = tagComboBox.getEditor().getText();

                // If the text is empty, return
                if (tag.isEmpty()) {
                    return;
                }

                if (availableTags.contains(tag)) {
                    // Remove the tag from the combobox
                    tagComboBox.getItems().remove(tag);
                }

                // Add the tag to the selected tags list and create a button for it
                selectedTags.add(tag);
                tagContainer.getChildren().add(createTagLabelButton(tag));

                // Clear the input field
                tagComboBox.getEditor().clear();
                tagComboBox.setValue(null);
            }
        });

        tagBox.getChildren().addAll(tagInuptLabel, tagComboBox);
        tagBox.setSpacing(10);
        tagBox.setPadding(new javafx.geometry.Insets(0, 30, 0, 20));

        tagContainer = new FlowPane();
        tagContainer.setHgap(10);
        tagContainer.setVgap(5);
        tagContainer.getChildren().addAll(tagBox, tagListLabel);
    }

    /**
     * Create a button for a tag that will be displayed in the UI The bottom
     * will remove the tag from the selected tags list when clicked
     */
    private Button createTagLabelButton(String name) {
        Button button = new Button(name);
        // Remove the tag from the selected tags list when the button is clicked
        button.setOnAction(event -> {
            if (selectedTags.contains(name)) {
                selectedTags.remove(name);
                if (availableTags.contains(name)) {
                    tagComboBox.getItems().add(name);
                }
            }
            tagContainer.getChildren().remove(button);
        });

        // Set the style of the button and add a tooltip
        button.getStyleClass().add("tag-button");
        button.setTooltip(new Tooltip("Click to remove the tag"));

        return button;
    }

    /**
     * Return null if the value is empty or null
     */
    private String nullIfEmpty(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }
}
