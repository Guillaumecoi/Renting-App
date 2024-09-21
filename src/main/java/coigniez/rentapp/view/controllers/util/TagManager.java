package coigniez.rentapp.view.controllers.util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lombok.Getter;

public class TagManager {

    private List<String> availableTags;
    @Getter
    private List<String> selectedTags = new ArrayList<>();
    private ComboBox<String> tagComboBox;
    private FlowPane tagContainer;
    @Getter
    private HBox pane;

    public TagManager(List<String> availableTags) {
        initialize(availableTags);
    }

    private void initialize(List<String> availableTags) {
        // Tag input section
        HBox tagInputBox = new HBox();
        Label tagInputLabel = new Label("Tags:");
        tagInputLabel.setMinWidth(50);
        tagComboBox = new ComboBox<>();
        tagComboBox.setTooltip(new Tooltip("Press enter to add the tag"));
        setAvailableTags(availableTags);

        tagComboBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String tag = tagComboBox.getEditor().getText();
                if (tag.isEmpty()) {
                    return;
                }
                addSelectedTag(tag);
                tagComboBox.getEditor().clear();
                tagComboBox.setValue(null);
            }
        });

        tagInputBox.getChildren().addAll(tagInputLabel, tagComboBox);
        tagInputBox.setSpacing(10);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Tag list section
        HBox tagListBox = new HBox();
        Label tagListLabel = new Label("Selected tags:");
        tagListLabel.setMinWidth(100);
        tagContainer = new FlowPane();
        tagContainer.setHgap(10);
        tagContainer.setVgap(5);

        tagListBox.getChildren().addAll(tagListLabel, tagContainer);
        tagListBox.setSpacing(10);

        // Main container
        pane = new HBox();
        pane.getChildren().addAll(tagInputBox, spacer, tagListBox);
        pane.setSpacing(10);
        pane.setPadding(new javafx.geometry.Insets(0, 30, 0, 20));
    }

    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = new ArrayList<>();
        tagContainer.getChildren().clear();
        selectedTags.stream().forEach(tag -> addSelectedTag(tag));
    }

    public void addSelectedTag(String tag) {
        if (availableTags.contains(tag)) {
            tagComboBox.getItems().remove(tag);
        }
        selectedTags.add(tag);
        tagContainer.getChildren().add(createTagLabelButton(tag));
    }

    public void setAvailableTags(List<String> availableTags) {
        this.availableTags = availableTags;
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(availableTags);
        tagComboBox.setEditable(true);
    }

    /**
     * Create a button for a tag that will be displayed in the UI The bottom
     * will remove the tag from the selected tags list when clicked
     */
    private Button createTagLabelButton(String name) {
        Button button = new Button(name);
        button.setOnAction(event -> {
            if (selectedTags.contains(name)) {
                selectedTags.remove(name);
                if (availableTags.contains(name)) {
                    tagComboBox.getItems().add(name);
                }
            }
            tagContainer.getChildren().remove(button);
        });
        button.getStyleClass().add("tag-button");
        button.setTooltip(new Tooltip("Click to remove the tag"));
        return button;
    }
}
