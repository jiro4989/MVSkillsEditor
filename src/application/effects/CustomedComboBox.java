package application.effects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;

public class CustomedComboBox extends ComboBox<String> {
  ObservableList<String> items;

  public CustomedComboBox() {
    setOnScroll(e -> changeSelectedItem(e));
    items = FXCollections.observableArrayList();
  }

  public CustomedComboBox(double width) {
    this();
    setPrefWidth(width);
  }

  public CustomedComboBox(String... values) {
    this();
    items = FXCollections.observableArrayList(values);
    setItems(items);
    getSelectionModel().select(0);
  }

  public CustomedComboBox(double width, String... values) {
    this(values);
    setPrefWidth(width);
  }

  private void changeSelectedItem(ScrollEvent e) {
    int selectedIndex = getSelectionModel().getSelectedIndex();
    selectedIndex = 0 < e.getDeltaY() ? Math.max(0, selectedIndex - 1)
        : Math.min(items.size() - 1, selectedIndex + 1);
    getSelectionModel().select(selectedIndex);
  }
}
