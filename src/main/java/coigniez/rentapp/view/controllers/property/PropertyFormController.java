package coigniez.rentapp.view.controllers.property;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.address.Country;
import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.model.property.PropertyService;
import coigniez.rentapp.model.property.tag.TagDTO;
import coigniez.rentapp.view.controllers.util.TagManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;

/**
 * Controller for the property form.
 *
 * The controller is responsible for handling the form submission and showing
 * the success alert.
 */
@Component
@FxmlView("/views/property/PropertyForm.fxml")
public class PropertyFormController {

    @Autowired
    private PropertyService propertyService;

    // The property to edit
    PropertyDTO property;

    // Helper classes for the form
    private PropertyForm propertyForm;
    private TagManager tagManager;

    @FXML
    private VBox formField;

    @FXML
    public void initialize() {
    }

    /**
     * try to add the property to the database
     */
    private void addProperty() {
        // Get the values from the form fields
        property = propertyForm.getProperty();
        property.setTagsFromList(tagManager.getSelectedTags());
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

    /**
     * Show the success alert
     */
    private void showSuccessAlert(PropertyDTO property) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Property Submitted Successfully");
        alert.setContentText("The property: \n" + property + "\nhas been submitted successfully.");

        ButtonType buttonTypeAddAnother = new ButtonType("Ok");

        alert.getButtonTypes().setAll(buttonTypeAddAnother);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeAddAnother) {
            // clear the form
            formField.getChildren().clear();
            setProperty(null);
        }
    }

    /**
     * Set the property to edit
     *
     * @param property the property to edit
     */
    public void setProperty(PropertyDTO property) {
        this.property = property;
        List<String> tags;
        String buttonLabel;
        if (property == null || property.getId() == null) {
            tags = null;
            buttonLabel = "Add Property";
            property = new PropertyDTO();
        } else {
            tags = property.getTags().stream().map(TagDTO::getName).toList();
            buttonLabel = "Edit Property";

        }
        tagManager = new TagManager(propertyService.findAllTags().stream().map(TagDTO::getName).toList(), tags);
        propertyForm = new PropertyForm(property, List.of(Country.getNames()));

        // Add a submit button
        Button submitButton = new Button(buttonLabel);
        submitButton.setOnAction(event -> addProperty());

        // Add the form and the submit button to the form field
        formField.getChildren().add(propertyForm.getFormRenderer());
        formField.getChildren().add(tagManager.getPane());
        formField.getChildren().add(submitButton);
        formField.setSpacing(10);
    }
}
