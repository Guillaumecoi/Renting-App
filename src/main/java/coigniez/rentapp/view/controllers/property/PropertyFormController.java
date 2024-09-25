package coigniez.rentapp.view.controllers.property;

import java.util.List;

import org.springframework.stereotype.Component;

import coigniez.rentapp.model.address.Country;
import coigniez.rentapp.model.property.PropertyDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
@FxmlView("/views/property/PropertyForm.fxml")
public class PropertyFormController {

    @Setter
    private PropertyController propertyController;

    PropertyDTO property;               // The property to edit
    private PropertyForm propertyForm;  // Helper class for the form

    @FXML
    private VBox formField;

    @FXML
    public void initialize() {
    }

    /**
     * Set the property to edit
     *
     * @param property the property to edit
     */
    public void setProperty(PropertyDTO property) {
        formField.getChildren().clear();
        this.property = property;
        String buttonLabel;
        if (property == null || property.getId() == null) {
            buttonLabel = "Add Property";
            property = new PropertyDTO();
        } else {
            buttonLabel = "Edit Property";
        }
        propertyForm = new PropertyForm(property, List.of(Country.getNames()), propertyController.getAllTags());

        // Add a submit button
        Button submitButton = new Button(buttonLabel);
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
            setProperty(null);
        }
    }
}
