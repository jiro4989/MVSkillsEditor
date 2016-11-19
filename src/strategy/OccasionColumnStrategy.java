package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class OccasionColumnStrategy extends ColumnStrategy {
  public OccasionColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).occasionProperty().get();
  }

  @Override
  public void setValue(Object value) {
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).occasionProperty().set(strValue);
    }
  }
}
