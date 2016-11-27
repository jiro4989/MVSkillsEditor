package application.tableview.strategy.cell;

import application.tableview.Skill;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DamageElementColumnStrategy extends ColumnStrategy {
  private ObservableList<String> items;

  public DamageElementColumnStrategy(TableView<Skill> tableView, int rowIndex,
      ObservableList<String> items) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.items = items;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).damageElementProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).damageElementProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    return items.parallelStream()
        .anyMatch(n -> n.equals(strValue));
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
