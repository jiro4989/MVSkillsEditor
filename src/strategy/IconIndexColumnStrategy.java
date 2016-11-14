package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class IconIndexColumnStrategy extends ColumnStrategy {
  public IconIndexColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).iconIndexProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setIconIndex((int) value);
  }

}
