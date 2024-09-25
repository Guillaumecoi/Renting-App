package coigniez.rentapp.view.controllers.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.view.controllers.MainController;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;

@Component
public class PropertyController {

    @Autowired
    private FxWeaver fxWeaver;

    @Setter
    private MainController mainController;

    @Getter
    private AnchorPane propertyAnchorPane = new AnchorPane();

    public void handleAddPropertyButtonAction(ActionEvent event) {
        editProperty(new PropertyDTO());
    }

    public void propertyListView() {
        try {
            Parent propertyListView = fxWeaver.loadView(PropertyListController.class);
            // Get the controller and set the main controller
            PropertyListController controller = fxWeaver.getBean(PropertyListController.class);
            controller.setPropertyController(this);
            // Set the PropertyListController as the only child of the AnchorPane
            propertyAnchorPane.getChildren().clear();
            propertyAnchorPane.getChildren().setAll(propertyListView);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error loading property list screen");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }

    public void showPropertyDetails(PropertyDTO property) {
        //TODO
    }

    public void editProperty(PropertyDTO property) {
        try {
            // Load the new FXML file for the PropertyFormController
            Parent addPropertyView = fxWeaver.loadView(PropertyFormController.class);

            // Get the controller using FxWeaver
            PropertyFormController controller = fxWeaver.getBean(PropertyFormController.class);

            // Pass attributes to the controller
            controller.setPropertyController(this);
            controller.setProperty(property);

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Property");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(propertyAnchorPane.getScene().getWindow());

            // Set the scene for the new stage
            Scene scene = new Scene(addPropertyView);
            popupStage.setScene(scene);

            // Show the new stage as a modal dialog
            popupStage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error loading add property screen");
            alert.showAndWait();
        }
    }
}
