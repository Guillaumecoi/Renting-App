package coigniez.rentapp.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.view.controllers.property.PropertyFormController;
import coigniez.rentapp.view.controllers.property.PropertyListController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/main.fxml")
public class MainController {

    @Autowired
    private FxWeaver fxWeaver;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void initialize() {
        mainAnchorPane.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        propertyListView();
    }

    @FXML
    public void handleAddPropertyButtonAction(ActionEvent event) {
        editProperty(new PropertyDTO());
    }

    public void propertyListView() {
        try {
            Parent propertyListView = fxWeaver.loadView(PropertyListController.class);
            // Get the controller and set the main controller
            PropertyListController controller = fxWeaver.getBean(PropertyListController.class);
            controller.setMainController(this);
            // Set the PropertyListController as the only child of the AnchorPane
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().setAll(propertyListView);
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
            controller.setProperty(property);

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Property");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(mainAnchorPane.getScene().getWindow());

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
