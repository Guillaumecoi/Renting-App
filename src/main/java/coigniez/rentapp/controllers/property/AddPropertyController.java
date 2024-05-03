package coigniez.rentapp.controllers.property;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/property/addProperty.fxml")
public class AddPropertyController {
    @FXML
    private AnchorPane formField;

    @FXML
    public void initialize() {
        System.out.println("AddPropertyController initialized");
        formField.getChildren().add(new javafx.scene.control.Label("Add Property Form"));
    }
}
