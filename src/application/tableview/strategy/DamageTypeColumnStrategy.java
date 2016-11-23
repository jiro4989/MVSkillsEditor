package application.tableview.strategy;

import application.tableview.Skill;
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
    tableView.getItems().get(rowIndex).damageTypeProperty().set((String) value);
  }

  @Override
  public boolean isInvokable(Object value) {
    // TODO 自動生成されたメソッド・スタブ
    return false;
  }
}
