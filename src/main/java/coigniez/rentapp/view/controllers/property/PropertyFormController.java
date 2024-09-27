package coigniez.rentapp.view.controllers.property;

import java.util.Optional;

import org.springframework.stereotype.Component;

import coigniez.rentapp.model.property.PropertyDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;

/**
 * Controller for the property form.
 *
 * The controller is responsible for handling the form submission and showing
 * the success alert.
 */
@Component
@FxmlView("/views/property/propertyForm.fxml")
public class PropertyFormController {

    @Setter
    private PropertyController propertyController;

    private PropertyDTO property;               // The property to edit
    private PropertyForm propertyForm;  // Helper class for the form
    private PropertyMode mode;          // The mode of the form

    @FXML
    private VBox formField;

    @FXML
    public void initialize() {
    }

    // The mode of the form
    public enum PropertyMode {
        NEW("Create New Property"),
        EDIT("Edit Property"),
        SUBPROPERTY("Add Subproperty");

        private final String buttonLabel;

        PropertyMode(String buttonLabel) {
            this.buttonLabel = buttonLabel;
        }

        public String getButtonLabel() {
            return buttonLabel;
        }
    }

    /**
     * Set the property to edit
     *
     * @param property the property to edit
     */
    public void setProperty(PropertyDTO property) {
        formField.getChildren().clear();
        // If the property is null, create a new property
        if (property == null) {
            property = new PropertyDTO();
        }
        this.property = property;
        // Get the mode based on the property
        getMode();
        // Ask the user if they want to copy the address and tags from the parent property
        getPopupQuestions();

        propertyForm = new PropertyForm(property.copy(), propertyController.getCountries(), propertyController.getAllTags());

        // Add a submit button
        Button submitButton = new Button(mode.getButtonLabel());
        submitButton.setOnAction(event -> addProperty());

        // Add the form and the submit button to the form field
        formField.getChildren().add(propertyForm.getFormPane());
        formField.getChildren().add(submitButton);
        formField.setSpacing(10);
    }

    /**
     * try to add the property to the database
     */
    private void addProperty() {
        // Get the values from the form fields
        property = propertyForm.getProperty();
        // Save the property
        boolean saved = propertyController.saveProperty(property);
        // Clear the form if the property was saved
        if (saved) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Property Submitted Successfully");
            alert.setHeaderText(null);
            alert.setContentText("The property: \n" + property + "\nhas been submitted successfully.");
            alert.showAndWait();
            setProperty(null);
        }
    }

    /**
     * Get the mode of the form based on the property
     */
    private void getMode() {
        if (property.getId() != null) {  // If the property has an id, the mode is edit
            mode = PropertyMode.EDIT;
        } else if (property.getParent() != null) {
            mode = PropertyMode.SUBPROPERTY;  // If the property has a parent but no id, the mode is subproperty
        } else {
            mode = PropertyMode.NEW;  // Otherwise, the mode is new
        }
    }

    private void getPopupQuestions() {
        if (mode == PropertyMode.SUBPROPERTY) {
            // Ask if the user wants to copy the address and tags from the parent property
            // Create a confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Copy Address and Tags");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to copy the address and tags from the parent property?");

            // Add the buttons
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Show the dialog and get the result
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                // Copy the address and tags from the parent property
                if (property.getParent().getAddress() != null) {
                    property.setAddress(property.getParent().getAddress().copy());
                }
                property.setTags(property.getParent().getTags());
            }
        }
    }
}
