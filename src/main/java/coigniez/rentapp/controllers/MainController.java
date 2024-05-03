package coigniez.rentapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.controllers.property.AddPropertyController;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

@Component
@FxmlView("/views/main.fxml")
public class MainController {

    @Autowired
    private FxWeaver fxWeaver;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void handleAddPropertyButtonAction(ActionEvent event) {
        try {
            // Load the new FXML file for the AddPropertyController
            Parent addPropertyView = fxWeaver.loadView(AddPropertyController.class);

            // Set the new FXML file as the child of the AnchorPane
            mainAnchorPane.getChildren().setAll(addPropertyView);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error loading add property screen");
            alert.showAndWait();
        }
    }
}
