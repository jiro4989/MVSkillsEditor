package application.tableview.strategy.record;

import application.tableview.Skill;
import application.tableview.SkillTableViewBorderPaneController;
import javafx.scene.control.TableView;

public class DeleteRecordStrategy extends RecordStrategy {
  private SkillTableViewBorderPaneController controller;

  public DeleteRecordStrategy(TableView<Skill> tableView, int rowIndex,
      SkillTableViewBorderPaneController controller) {
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.controller = controller;
  }

  @Override
  public void execute() {
    Skill item = tableView.getItems().get(rowIndex);
    tableView.getItems().remove(item);
    controller.updateId();
  }

  @Override
  public boolean isInvokable(Object value) {
    return true;
  }
}
