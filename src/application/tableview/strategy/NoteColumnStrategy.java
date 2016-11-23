package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class NoteColumnStrategy extends ColumnStrategy {
  public NoteColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).noteProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).noteProperty().set((String) value);
  }

  @Override
  public boolean isInvokable(Object value) {
    return true;
  }
}
