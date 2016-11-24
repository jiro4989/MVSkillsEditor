package application.tableview.strategy;

import application.tableview.Skill;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class RequiredWtypeId1ColumnStrategy extends ColumnStrategy {
  private ObservableList<String> items;

  public RequiredWtypeId1ColumnStrategy(TableView<Skill> tableView, int rowIndex,
      ObservableList<String> items) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.items = items;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).requiredWtypeId1Property().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).requiredWtypeId1Property().set((String) value);
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
