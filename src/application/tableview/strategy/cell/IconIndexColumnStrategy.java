package application.tableview.strategy.cell;

import application.tableview.Skill;
import application.tableview.cell.IconTableCell;
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
    String strValue = (String) value;
    if (strValue.matches(NUMBER_REGEX)) {
      tableView.getItems().get(rowIndex).iconIndexProperty().set((String) value);
    }
  }

  @Override
  public boolean isInvokable(Object value) {
    String strValue = (String) value;
    if (strValue.matches(NUMBER_REGEX)) {
      int number = Integer.parseInt(strValue);
      return (0 <= number && number < IconTableCell.getIconImageMaxIndex());
    }
    return false;
  }

  @Override
  public String defaultValue(String value) {
    return numberFixer(value, 0, IconTableCell.getIconImageMaxIndex());
  }

}
