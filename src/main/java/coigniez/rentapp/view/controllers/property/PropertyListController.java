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
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/views/property/PropertyList.fxml")
public class PropertyListController {

    @Autowired
    private PropertyService propertyService;

    @FXML
    private TableView<PropertyDTO> propertyTable;

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
    }
}
