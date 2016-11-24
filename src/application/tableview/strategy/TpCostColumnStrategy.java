package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class TpCostColumnStrategy extends ColumnStrategy {
  public TpCostColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).tpCostProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).tpCostProperty().set((String)value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    if (strValue.matches(NUMBER_REGEX)) {
      int number = Integer.parseInt(strValue);
      return (0 <= number && number <= 100);
    }
    return false;
  }
}
