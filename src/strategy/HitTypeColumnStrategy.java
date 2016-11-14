package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class HitTypeColumnStrategy extends ColumnStrategy {
  public HitTypeColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).hitTypeProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setHitType((int) value);
  }
}
