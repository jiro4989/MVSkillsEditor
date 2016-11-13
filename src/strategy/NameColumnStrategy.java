package strategy;

import application.Skill;
import javafx.scene.control.TableView;

public class NameColumnStrategy implements ColumnStrategy {
  private TableView<Skill> tableView;
  private int rowIndex;

  public NameColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).nameProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setName((String)value);
  }
}
