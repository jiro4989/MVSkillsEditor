package application.tableview.strategy.cell;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class Message2ColumnStrategy extends ColumnStrategy {
  public Message2ColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).message2Property().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).message2Property().set((String) value);
  }

  @Override
  public boolean isInvokable(Object value) {
    return true;
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
