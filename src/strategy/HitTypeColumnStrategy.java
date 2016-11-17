package strategy;

import application.tableview.Skill;
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
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).setHitType((String) value);
    }
  }
}
