package coigniez.rentapp.view.controllers.property;

import java.util.Collections;
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
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.20));

        TableColumn<PropertyDTO, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> {
            AddressDTO address = cellData.getValue().getAddress();
            return new SimpleStringProperty(address != null ? address.toString() : "");
        });
        addressColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.50));

        TableColumn<PropertyDTO, String> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(cellData -> {
            Set<TagDTO> tags = cellData.getValue().getTags();
            return new SimpleStringProperty(tags != null ? tags.stream().map(TagDTO::getName).collect(Collectors.joining(", ")) : "");
        });
        tagsColumn.prefWidthProperty().bind(propertyTable.widthProperty().multiply(0.29));

        // Add columns to table
        Collections.addAll(propertyTable.getColumns(), nameColumn, addressColumn, tagsColumn);

        // Add properties to table
        propertyTable.getItems().addAll(properties);

        // Add double click event
        propertyTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleOpenAction();
            }
        });

        // Right click action
        propertyTable.setOnContextMenuRequested(event -> showContextMenu(event.getScreenX(), event.getScreenY()));
    }

    /**
     * Show context menu
     */
    private void showContextMenu(double screenX, double screenY) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem open = new MenuItem("Open");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");

        open.setOnAction(event -> handleOpenAction());
        edit.setOnAction(event -> handleEditAction());
        delete.setOnAction(event -> handleDeleteAction());

        contextMenu.getItems().addAll(open, edit, delete);
        contextMenu.show(propertyTable, screenX, screenY);
        // Add event filter to hide context menu on mouse click outside
        propertyTable.getScene().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, e -> contextMenu.hide());
    }

    /**
     * Opens the detail page of selected property
     */
    private void handleOpenAction() {
        PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();
        mainController.showPropertyDetails(selectedProperty);
    }

    /**
     * Opens an edit page for the selected property
     */
    private void handleEditAction() {
        PropertyDTO selectedProperty = propertyTable.getSelectionModel().getSelectedItem();
        mainController.editProperty(selectedProperty);
    }

    /**
     * Deletes the selected property
     */
    private void handleDeleteAction() {
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
    }
}
