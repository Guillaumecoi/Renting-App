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

    private PropertyForm propertyForm;
    private TagManager tagManager;

    @FXML
    private VBox formField;

    @FXML
    public void initialize() {
        property = new PropertyDTO();
        tagManager = new TagManager(propertyService.findAllTags().stream().map(TagDTO::getName).toList());
        propertyForm = new PropertyForm(property, List.of(Country.getNames()));

        // Add a submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> addProperty());

        // Add the form and the submit button to the form field
        formField.getChildren().add(propertyForm.getFormRenderer());
        formField.getChildren().add(tagManager.getPane());
        formField.getChildren().add(submitButton);
        formField.setSpacing(10);
    }

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
}
