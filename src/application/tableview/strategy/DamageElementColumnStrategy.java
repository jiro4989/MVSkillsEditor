package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class DamageElementColumnStrategy extends ColumnStrategy {
  public DamageElementColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).damageElementProperty().get();
  }

  @Override
  public void setValue(Object value) {
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).damageElementProperty().set((String)value);
    }
  }
}
