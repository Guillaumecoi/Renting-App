package coigniez.rentapp.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.view.controllers.property.PropertyFormController;
import coigniez.rentapp.view.controllers.property.PropertyListController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
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
        homeScreen();
    }

    @FXML
    public void handleAddPropertyButtonAction(ActionEvent event) {
        try {
            // Load the new FXML file for the AddPropertyController
            Parent addPropertyView = fxWeaver.loadView(PropertyFormController.class);

            // Get the controller using FxWeaver
            PropertyFormController controller = fxWeaver.getBean(PropertyFormController.class);

            // Pass attributes to the controller
            controller.setParent(this);

            // Set the new FXML file as the child of the AnchorPane
            mainAnchorPane.getChildren().setAll(addPropertyView);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error loading add property screen");
            alert.showAndWait();

        }
    }

    public void homeScreen() {
        // Clear the AnchorPane
        mainAnchorPane.getChildren().clear();

        // Load the PropertyListController
        try {
            Parent propertyListView = fxWeaver.loadView(PropertyListController.class);
            mainAnchorPane.getChildren().setAll(propertyListView);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error loading property list screen");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }
}
