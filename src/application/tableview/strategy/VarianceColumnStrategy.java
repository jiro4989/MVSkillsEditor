package application.tableview.strategy;

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
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).varianceProperty().set(strValue);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    // TODO 自動生成されたメソッド・スタブ
    return false;
  }
}
