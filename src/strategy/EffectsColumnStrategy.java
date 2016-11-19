package strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

public class EffectsColumnStrategy extends ColumnStrategy {
  public EffectsColumnStrategy(TableView<Skill> tableView, int rowIndex) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).effectsProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).effectsProperty().set((String)value);
  }
}
