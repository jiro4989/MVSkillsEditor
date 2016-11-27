package application.tableview.strategy.cell;

import application.tableview.Skill;
import javafx.scene.control.TableView;
import util.dictionary.SkillOccasion;

public class OccasionColumnStrategy extends ColumnStrategy {
  public OccasionColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).occasionProperty().get();
  }

  @Override
  public void setValue(Object value) {
    if (this.isInvokable(value)) {
      tableView.getItems().get(rowIndex).occasionProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    return SkillOccasion.getNameList().parallelStream()
        .anyMatch(n -> n.equals(strValue));
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
