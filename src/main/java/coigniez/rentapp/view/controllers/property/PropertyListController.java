package coigniez.rentapp.view.controllers.property;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coigniez.rentapp.model.address.AddressDTO;
import coigniez.rentapp.model.property.PropertyDTO;
import coigniez.rentapp.model.property.PropertyService;
import coigniez.rentapp.model.property.tag.TagDTO;
import coigniez.rentapp.view.controllers.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/property/PropertyList.fxml")
public class PropertyListController {

    @Autowired
    private PropertyService propertyService;

    @FXML
    private TableView<PropertyDTO> propertyTable;

    @Setter
    private MainController mainController;

    @FXML
    public void initialize() {
        // Fetch properties
        List<PropertyDTO> properties = propertyService.findAllProperties();
        initializeTable(properties);

    }

    private void initializeTable(List<PropertyDTO> properties) {
        // Initialize table columns
        TableColumn<PropertyDTO, String> nameColumn = new TableColumn<>("Name");
        TableColumn<PropertyDTO, String> addressColumn = new TableColumn<>("Address");
        TableColumn<PropertyDTO, String> tagsColumn = new TableColumn<>("Tags");

        // Set cell value factories
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> {
            AddressDTO address = cellData.getValue().getAddress();
            return new SimpleStringProperty(address != null ? address.toString() : "");
        });

        tagsColumn.setCellValueFactory(cellData -> {
            Set<TagDTO> tags = cellData.getValue().getTags();
            return new SimpleStringProperty(tags != null ? tags.stream().map(TagDTO::getName).collect(Collectors.joining(", ")) : "");
        });

        nameColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.20));
        addressColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.50));
        tagsColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.29));

        // Add columns to table
        propertyTable.getColumns().add(nameColumn);
        propertyTable.getColumns().add(addressColumn);
        propertyTable.getColumns().add(tagsColumn);

        // Add properties to table
        propertyTable.getItems().addAll(properties);

        // Add double click event
        propertyTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();
                mainController.showPropertyDetails(selectedProperty);
            }
        });

        // Add right click event
        propertyTable.setOnContextMenuRequested(event -> {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem open = new MenuItem("Open");
            MenuItem edit = new MenuItem("Edit");
            MenuItem delete = new MenuItem("Delete");

            open.setOnAction(event1 -> {
                PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();
                mainController.showPropertyDetails(selectedProperty);
            });

            edit.setOnAction(event1 -> {
                PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();
                mainController.editProperty(selectedProperty);
            });

            delete.setOnAction(event1 -> {
                PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();

                if (selectedProperty != null) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this property?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            propertyService.deleteProperty(selectedProperty.getId());
                            propertyTable.getItems().remove(selectedProperty);
                        }
                    });
                }
            });

            contextMenu.getItems().addAll(open, edit, delete);
            contextMenu.show(propertyTable, event.getScreenX(), event.getScreenY());

            // Add event filter to hide context menu on mouse click outside
            propertyTable.getScene().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, e -> {
                contextMenu.hide();
            });
        });
    }
}
