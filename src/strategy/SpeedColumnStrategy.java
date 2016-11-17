package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class SpeedColumnStrategy extends ColumnStrategy {
  public SpeedColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).speedProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setSpeed((int) value);
  }
}
