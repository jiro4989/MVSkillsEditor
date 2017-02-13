package application.tableview.strategy.record;

import application.tableview.Skill;
import application.tableview.command.ICommand;
import javafx.scene.control.TableView;

/**
 * テーブルのセルデータの更新コマンドクラス。
 * @author jiro
 */
public class TableRecordUpdateCommand implements ICommand {
  private TableView<Skill> tableView = new TableView<>();
  int rowIndex;
  private RecordStrategy prevStrategy;
  private RecordStrategy newStrategy;

  public TableRecordUpdateCommand(TableView<Skill> tableView, int rowIndex,
      RecordStrategy prevStrategy, RecordStrategy newStrategy) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.prevStrategy = prevStrategy;
    this.newStrategy = newStrategy;
  }

  @Override
  public void invoke() {
    newStrategy.execute();
  }

  @Override
  public void undo() {
    prevStrategy.execute();
  }

  @Override
  public void redo() {
    newStrategy.execute();
  }

  @Override
  public String toString() {
    return String.format(
        "tableView: %s, rowIndex: %d, strategy: %s, prevStrategy: %s, newStrategy: %s.",
        tableView, rowIndex, prevStrategy, newStrategy);
  }

}
