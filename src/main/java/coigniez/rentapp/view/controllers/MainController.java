package coigniez.rentapp.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.view.controllers.property.PropertyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/main.fxml")
public class MainController {

    @Autowired
    private PropertyController propertyController;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void initialize() {
        propertyController.setMainController(this);
        propertyController.propertyListView();
        mainAnchorPane.getChildren().add(propertyController.getPropertyAnchorPane());
    }

    @FXML
    public void handleAddPropertyButtonAction(ActionEvent event) {
        propertyController.editProperty(new PropertyDTO());
    }
}
