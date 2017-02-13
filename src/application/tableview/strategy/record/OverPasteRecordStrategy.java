package application.tableview.strategy.record;

import application.tableview.Skill;
import application.tableview.SkillTableViewBorderPaneController;
import javafx.scene.control.TableView;

public class OverPasteRecordStrategy extends RecordStrategy {
  private SkillTableViewBorderPaneController controller;
  private Skill prevRecord;

  public OverPasteRecordStrategy(TableView<Skill> tableView, int rowIndex,
      SkillTableViewBorderPaneController controller, Skill prevRecord) {
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.controller = controller;
    this.prevRecord = prevRecord != null ? prevRecord : getDefaultSkillRecord();
  }

  @Override
  public void execute() {
    tableView.getItems().set(rowIndex, prevRecord);
    tableView.getSelectionModel().select(rowIndex - 1);
    controller.updateId();
  }

  @Override
  public boolean isInvokable(Object value) {
    return true;
  }

}
