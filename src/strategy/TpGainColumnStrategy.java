package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class TpGainColumnStrategy extends ColumnStrategy {
  public TpGainColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).tpGainProperty().get();
  }

  @Override
  public void setValue(Object value) {
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).tpGainProperty().set(strValue);
    }
  }
}
