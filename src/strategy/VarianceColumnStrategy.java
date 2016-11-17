package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class VarianceColumnStrategy extends ColumnStrategy {
  public VarianceColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).varianceProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setVariance((String) value);
  }
}
