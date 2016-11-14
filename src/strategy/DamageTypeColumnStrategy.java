package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class DamageTypeColumnStrategy extends ColumnStrategy {
  public DamageTypeColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).damageTypeProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setDamageType((int) value);
  }
}
