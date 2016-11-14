package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class RequiredWtypeId1ColumnStrategy extends ColumnStrategy {
  public RequiredWtypeId1ColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).requiredWtypeId1Property().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setRequiredWtypeId1((int) value);
  }
}
