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

/**
 * A manager for tags that can be selected in a UI.
 *
 * The manager contains a combobox for selecting tags and a list of selected
 * tags that can be removed.
 */
public class TagManager {

    // Attributes
    private List<String> availableTags;  // The list of available tags for the combobox
    @Getter
    private List<String> selectedTags;

    // JavaFX components
    private ComboBox<String> tagComboBox;
    private final FlowPane tagContainer;
    @Getter
    private final HBox pane;

    /**
     * Create a new tag manager.
     *
     * @param availableTags the list of available tags for the combobox
     */
    public TagManager(List<String> availableTags, List<String> selectedTags) {
        // Tag input section
        HBox tagInputBox = new HBox();
        Label tagInputLabel = new Label("Tags:");
        tagInputLabel.setMinWidth(50);
        tagComboBox = new ComboBox<>();
        tagComboBox.setTooltip(new Tooltip("Press enter to add the tag"));
        internalSetAvailableTags(availableTags);

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

        // Set the selected tags
        internalSetSelectedTags(selectedTags);
    }

    /**
     * Private method to set the selected tags for the manager.
     *
     * @param selectedTags the list of selected tags
     */
    private void internalSetSelectedTags(List<String> selectedTags) {
        this.selectedTags = new ArrayList<>();
        tagContainer.getChildren().clear();
        if (selectedTags == null) {
            return;
        }
        selectedTags.forEach(this::addSelectedTag);
    }

    /**
     * Add a tag to the selected tags list.
     *
     * @param tag the tag to add
     */
    private void addSelectedTag(String tag) {
        if (availableTags.contains(tag)) {
            tagComboBox.getItems().remove(tag);
        }
        selectedTags.add(tag);
        tagContainer.getChildren().add(createTagLabelButton(tag));
    }

    /**
     * Private method to set the available tags for the combobox.
     *
     * @param availableTags the list of available tags
     */
    private void internalSetAvailableTags(List<String> availableTags) {
        this.availableTags = availableTags;
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(availableTags);
        tagComboBox.setEditable(true);
    }

    /**
     * Set the selected tags for the manager.
     *
     * @param selectedTags the list of selected tags
     */
    public void setSelectedTags(List<String> selectedTags) {
        internalSetSelectedTags(selectedTags);
    }

    /**
     * Set the available tags for the combobox.
     *
     * @param availableTags the list of available tags
     */
    public void setAvailableTags(List<String> availableTags) {
        internalSetAvailableTags(availableTags);
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
