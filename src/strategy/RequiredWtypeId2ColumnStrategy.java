package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class RequiredWtypeId2ColumnStrategy extends ColumnStrategy {
  public RequiredWtypeId2ColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).requiredWtypeId2Property().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setRequiredWtypeId2((String) value);
  }
}
