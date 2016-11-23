package application.tableview;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

/**
 * 使わない可能性が高い。
 * @author jiro
 */
public class CustomedComboBoxTableCell extends ComboBoxTableCell<Skill, String> {
  private ComboBox<String> comboBox;
  private final ObservableList<String> items;

  public CustomedComboBoxTableCell(ObservableList<String> items) {
    this.items = items;
  }

  @Override
  public void startEdit() {
    if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
      return;
    }

    if (comboBox == null) {
      comboBox = createComboBox(this, items, converterProperty().get());
      comboBox.editableProperty().bind(comboBoxEditableProperty());
    }

    comboBox.getSelectionModel().select(getItem());

    super.startEdit();
    setText(null);
    setGraphic(comboBox);
    comboBox.requestFocus();
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
  }

  /** {@inheritDoc} */
  @Override
  public void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
  }

  private ComboBox<String> createComboBox(final CustomedComboBoxTableCell cell,
      ObservableList<String> list, StringConverter<String> converter) {
    ComboBox<String> comboBox = new ComboBox<>(list);
    comboBox.setOnKeyReleased(c -> {
      if (c.getCode() == KeyCode.ESCAPE) {
        cell.cancelEdit();
        c.consume();
      } else if (c.getCode() == KeyCode.SPACE) {
        if (converter == null) {
          throw new IllegalStateException(
              "Attempting to convert text input into Object, but provided "
                  + "StringConverter is null. Be sure to set a StringConverter "
                  + "in your cell factory.");
        }
        cell.commitEdit(converter.fromString(comboBox.getValue()));
        c.consume();
        cell.requestFocus();
      }
    });

    return comboBox;
  }
}
