package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class RepeatsColumnStrategy extends ColumnStrategy {
  public RepeatsColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).repeatsProperty().get();
  }

  @Override
  public void setValue(Object value) {
    String strValue = (String) value;
    if (strValue.matches(REGEX)) {
      tableView.getItems().get(rowIndex).repeatsProperty().set(strValue);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    // TODO 自動生成されたメソッド・スタブ
    return false;
  }
}
