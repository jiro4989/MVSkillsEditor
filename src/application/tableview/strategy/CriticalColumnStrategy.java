package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;
import util.dictionary.SkillCritical;

public class CriticalColumnStrategy extends ColumnStrategy {
  public CriticalColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).criticalProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).criticalProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    return SkillCritical.getNameList().parallelStream()
        .anyMatch(n -> n.equals(strValue));
  }
}
