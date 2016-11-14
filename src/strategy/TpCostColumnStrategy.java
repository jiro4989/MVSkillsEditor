package strategy;

import application.Skill;
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
    tableView.getItems().get(rowIndex).setTpCost((int) value);
  }
}
