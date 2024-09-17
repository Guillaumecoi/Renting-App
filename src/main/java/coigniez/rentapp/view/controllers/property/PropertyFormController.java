package coigniez.rentapp.view.controllers.property;

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
import net.rgielen.fxweaver.core.FxmlView;

import java.util.ArrayList;
import java.util.List;

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
@FxmlView("/views/property/PropertyForm.fxml")
public class PropertyFormController {

    @Autowired
    private PropertyService propertyService;

    @FXML
    private VBox formField;

    private Form form;
    private StringField nameField;
    private FlowPane tagContainer;
    ComboBox<String> tagComboBox;
    private List<String> availableTags;
    private List<String> selectedTags = new ArrayList<>();

    @FXML
    public void initialize() {
        // Set the fields of the form
        nameField = Field.ofStringType("Property Name")
                .label("Name")
                .required("This field can’t be empty");

        // Create the form
        form = Form.of(
                Group.of(nameField)).title("Property Form");

        initializeTags();

        // Render the form
        formField.getChildren().add(new FormRenderer(form));
        formField.getChildren().add(tagContainer);
        formField.setSpacing(10);

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
            alert.setTitle("Error");
            alert.setHeaderText("Unable to add property");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void initializeTags() {
        // Get the available tags
        availableTags = propertyService.findAllTags();

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
     * Create a button for a tag that will be displayed in the UI
     * The bottom will remove the tag from the selected tags list when clicked
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
}