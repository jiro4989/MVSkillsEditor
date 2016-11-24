package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class DescriptionColumnStrategy extends ColumnStrategy {
  public DescriptionColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).descriptionProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).descriptionProperty().set((String) value);
  }

  @Override
  public boolean isInvokable(Object value) {
    return true;
  }

  @Override
  public String defaultValue(String value) {
    return null;
  }
}
