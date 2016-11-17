package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class MpCostColumnStrategy extends ColumnStrategy {
  public MpCostColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).mpCostProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setMpCost((String) value);
  }
}
