package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;
import util.dictionary.SkillScope;

public class StypeIdColumnStrategy extends ColumnStrategy {
  public StypeIdColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).stypeIdProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).stypeIdProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    return false;
  }
}
