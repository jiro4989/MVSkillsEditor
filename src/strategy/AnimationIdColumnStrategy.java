package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class AnimationIdColumnStrategy extends ColumnStrategy {
  public AnimationIdColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).animationIdProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).setAnimationId((String) value);
  }
}
