package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class ScopeColumnStrategy extends ColumnStrategy {
  public ScopeColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).scopeProperty().get();
  }

  @Override
  public void setValue(Object value) {
    String strValue = (String) value;
    tableView.getItems().get(rowIndex).scopeProperty().set(strValue);
//    if (strValue.matches(REGEX)) {
//      tableView.getItems().get(rowIndex).scopeProperty().set(strValue);
//    }
  }
}
