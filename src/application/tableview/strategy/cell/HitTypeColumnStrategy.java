package application.tableview.strategy.cell;

import application.tableview.Skill;
import javafx.scene.control.TableView;
import util.dictionary.SkillHitType;

public class HitTypeColumnStrategy extends ColumnStrategy {
  public HitTypeColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).hitTypeProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).hitTypeProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    return SkillHitType.getNameList().parallelStream()
        .anyMatch(n -> n.equals(strValue));
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
