package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class CriticalColumnStrategy extends ColumnStrategy {
  public CriticalColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).criticalProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setCritical((int) value);
  }
}
