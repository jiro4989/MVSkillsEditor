package application.tableview.strategy.cell;

import application.tableview.Skill;
import javafx.scene.control.TableView;
import util.dictionary.SkillScope;

public class ScopeColumnStrategy extends ColumnStrategy {
  public ScopeColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).scopeProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).scopeProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    return SkillScope.getNameList().parallelStream()
        .anyMatch(n -> n.equals(strValue));
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
