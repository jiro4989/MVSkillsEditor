package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class IdColumnStrategy extends ColumnStrategy {
  public IdColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).idProperty().get();
  }

  @Override
  public void setValue(Object value) {
  }

  @Override
  public boolean isInvokable(Object value) {
    return false;
  }
}
