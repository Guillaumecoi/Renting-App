package coigniez.rentapp.view.controllers.property;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.renderer.FormRenderer;

import coigniez.rentapp.exceptions.InvalidAddressException;
import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.model.property.PropertyService;

@Component
@FxmlView("/views/property/addProperty.fxml")
public class AddPropertyController {

    @Autowired
    private PropertyService propertyService;

    @FXML
    private VBox formField;

    private Form form;
    private StringField nameField;

    @FXML
    public void initialize() {
        nameField = Field.ofStringType("Name")
                .label("Name")
                .required("This field can’t be empty");

        // Create the form
        form = Form.of(
                Group.of(nameField)).title("Property Form");

        // Render the form
        formField.getChildren().add(new FormRenderer(form));

        // Add a submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> addProperty());
        formField.getChildren().add(submitButton);
    }

    private void addProperty() {
        try {
            PropertyDTO property = new PropertyDTO();
            property.setName(nameField.getValue());
            propertyService.saveProperty(property);
        } catch (InvalidAddressException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}