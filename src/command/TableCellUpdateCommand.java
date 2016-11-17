package command;

import application.Skill;
import javafx.scene.control.TableView;
import strategy.ColumnStrategy;

/**
 * テーブルのセルデータの更新コマンドクラス。
 * @author jiro
 */
public class TableCellUpdateCommand implements ICommand {
  private TableView<Skill> tableView = new TableView<>();
  private ColumnStrategy strategy;
  int rowIndex;
  int columnIndex;
  private String prevText;
  private String newText;

  public TableCellUpdateCommand(TableView<Skill> tableView, int rowIndex, int columnIndex,
      String newText, ColumnStrategy strategy) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.prevText = (String)strategy.getValue();
    this.newText = newText;
    this.strategy = strategy;
  }

  @Override
  public void invoke() {
    prevText = (String) strategy.getValue();
    strategy.setValue(newText);
    reDraw();
  }

  @Override
  public void undo() {
    strategy.setValue(prevText);
    reDraw();
  }

  @Override
  public void redo() {
    strategy.setValue(newText);
    reDraw();
  }

  private void reDraw() {
    tableView.getColumns().get(columnIndex).setVisible(false);
    tableView.getColumns().get(columnIndex).setVisible(true);
  }

  @Override
  public String toString() {
    return String.format("tableView: %s, rowIndex: %d, columnIndex: %d, prevText: %s, newText: %s.",
        tableView, rowIndex, columnIndex, prevText, newText);
  }

}
