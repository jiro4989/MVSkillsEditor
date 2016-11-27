package application.tableview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import application.tableview.strategy.record.DeleteRecordStrategy;
import application.tableview.strategy.record.InsertNewRecordStrategy;
import application.tableview.strategy.record.RecordStrategy;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import jiro.lib.java.util.PropertiesHundler;
import util.UtilInteger;

public class TableViewManager {
  private PropertiesHundler columnIndexProp;
  private PropertiesHundler columnWidthProp;

  private TableView<Skill> tableView;
  private SkillTableViewBorderPaneController controller;

  private final MenuItem copyItem;
  private final MenuItem pasteItem;
  private final MenuItem insertNewRecordItem;
  private final MenuItem cutRecordItem;
  private final MenuItem copyRecordItem;
  private final MenuItem pasteRecorditem;
  private final MenuItem deleteRecordItem;
  private final MenuItem selectAllItem;

  @SuppressWarnings("unchecked")
  public TableViewManager(
      TableView<Skill> aTableView,
      SkillTableViewBorderPaneController aController,
      String propTitle) {
    tableView = aTableView;
    controller = aController;

    columnIndexProp = new PropertiesHundler(propTitle + "-column-index");
    columnWidthProp = new PropertiesHundler(propTitle + "-column-width");
    settingTableView(tableView);

    tableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldValue, newValue) -> {
          controller.updateEffectsPane();
          controller.updateNotePane();
          controller.changeDisablePreviews(false);
        });

    tableView.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.getTableColumn() != null) {
        int columnIndex = newVal.getColumn();
        int rowIndex = tableView.getSelectionModel().getSelectedIndex();
        controller.updateAxisLabels(columnIndex, rowIndex);
      }
    });

    tableView.getColumns().forEach(c -> settingTableColumn((TableColumn<Skill, String>) c));

    copyItem = new MenuItem("選択中のセルをコピー");
    copyItem.setOnAction(e -> copyValueOfSelectedCells());
    pasteItem = new MenuItem("選択中のセルから貼り付け");
    pasteItem.setOnAction(e -> pasteValue());

    insertNewRecordItem = new MenuItem("新しい行データを挿入");
    insertNewRecordItem.setOnAction(e -> insertNewRecord());
    cutRecordItem = new MenuItem("選択行を切り取り");
    copyRecordItem = new MenuItem("選択行をコピー");
    pasteRecorditem = new MenuItem("行データを貼り付け");
    deleteRecordItem = new MenuItem("選択行を削除");
    deleteRecordItem.setOnAction(e -> deleteRecord());
    selectAllItem = new MenuItem("全選択");

    ContextMenu menu = new ContextMenu(copyItem, pasteItem, new SeparatorMenuItem(),
        cutRecordItem, copyRecordItem, pasteRecorditem, insertNewRecordItem, deleteRecordItem,
        new SeparatorMenuItem(), selectAllItem);
    menu.setOnShown(e -> contextMenuOnShown());
    tableView.setContextMenu(menu);
  }

  private void settingTableView(TableView<Skill> tableView) {
    TableView.TableViewSelectionModel<Skill> model = tableView.getSelectionModel();
    model.setSelectionMode(SelectionMode.MULTIPLE);
    model.setCellSelectionEnabled(true);
    tableView.setFixedCellSize(50);
    setColumnIndex();
    setColumnWidth();
  }

  /**
   * プロパティファイルからカラムインデックスを読み取り、カラム位置を設定する。
   */
  private void setColumnIndex() {
    if (columnIndexProp.exists()) {
      columnIndexProp.load();

      ObservableList<TableColumn<Skill, ?>> columns = tableView.getColumns();
      int size = columns.size();

      List<TableColumn<Skill, ?>> columnList = new ArrayList<>(size);
      List<String> keysList = new ArrayList<>(size);
      List<Integer> columnIndices = new ArrayList<>(size);

      IntStream.range(0, size)
          .forEach(i -> {
            columnList.add(columns.get(i));

            String id = columns.get(i).getId();
            keysList.add(id);

            int index = Integer.parseInt(columnIndexProp.getValue(id));
            columnIndices.add(index);
          });
      columns.clear();

      IntStream.range(0, size)
          .forEach(i -> {
            for (int j = 0; j < size; j++) {
              if (i == columnIndices.get(j)) {
                columns.add(i, columnList.get(j));
                break;
              }
            }
          });
    }
  }

  private void setColumnWidth() {
    if (columnWidthProp.exists()) {
      columnWidthProp.load();

      ObservableList<TableColumn<Skill, ?>> columns = tableView.getColumns();
      int size = columns.size();
      IntStream.range(0, size)
          .forEach(i -> {
            TableColumn<Skill, ?> col = columns.get(i);
            double value = Double.parseDouble(columnWidthProp.getValue(col.getId()));
            col.setPrefWidth(value);;
          });
    }
  }

  private void settingTableColumn(TableColumn<Skill, String> tableColumn) {
    tableColumn.setSortable(false);
    tableColumn.setOnEditCommit(e -> {
      tableView.requestFocus();
      controller.insertText(e.getNewValue());
    });
  }

  /**
   * 前のセルに選択を移す.
   */
  void movePrevious() {
    if (this.isSelected()) {
      tableView.getSelectionModel().selectAboveCell();
    }
  }

  /**
   * 次のセルに選択を移す.
   */
  void moveNext() {
    if (this.isSelected()) {
      tableView.getSelectionModel().selectBelowCell();
    }
  }

  boolean isSelected() {
    return !tableView.getSelectionModel().isEmpty();
  }

  void outputPropertiesFile() {
    outputColumnIndex();
    outputColumnWidth();
  }

  private void outputColumnIndex() {
    ObservableList<TableColumn<Skill, ?>> columns = tableView.getColumns();
    IntStream.range(0, columns.size())
        .forEach(i -> {
          columnIndexProp.setValue(columns.get(i).getId(), "" + columns.indexOf(columns.get(i)));
        });
    columnIndexProp.write();
  }

  private void outputColumnWidth() {
    ObservableList<TableColumn<Skill, ?>> columns = tableView.getColumns();
    IntStream.range(0, columns.size())
        .forEach(i -> {
          columnWidthProp.setValue(columns.get(i).getId(), "" + columns.get(i).getWidth());
        });
    columnWidthProp.write();
  }

  private static List<String> copyValues;

  private void contextMenuOnShown() {
    pasteItem.setDisable(copyValues == null);
  }

  /**
   * 選択中のセルの値をクリップボードにコピーする。
   * この時、コピーされる値は最初にクリックしたセルと同じカラムのもののみを対象とする。
   */
  void copyValueOfSelectedCells() {
    if (this.isSelected()) {
      ObservableList<Integer> indicies = tableView.getSelectionModel().getSelectedIndices();
      copyValues = new ArrayList<>(indicies.size());
      for (int index : indicies) {
        @SuppressWarnings("unchecked")
        TablePosition<Skill, String> pos = tableView.getSelectionModel().getSelectedCells().get(0);
        TableColumn<Skill, String> column = pos.getTableColumn();
        String text = column.getCellData(index);
        copyValues.add(text);
      }
    }
  }

  /**
   * クリップボードにコピーされている値をペーストする。
   * 書式に即していないテキストの場合はペーストは実行されない。
   */
  void pasteValue() {
    if (this.isSelected() && copyValues != null) {
      int size = copyValues.size();
      int start = getSelectedCellRowIndex();
      int end = start + size - 1;

      AtomicInteger index = new AtomicInteger(0);
      AtomicInteger overCount = new AtomicInteger(0);
      IntStream.rangeClosed(start, end)
          .forEach(rowIndex -> {
            if (tableView.getItems().size() - 1 < rowIndex) {
              overCount.getAndIncrement();
              return;
            }
            String newText = copyValues.get(index.getAndIncrement());
            controller.invoke(tableView, newText, rowIndex);
          });
      controller.pushUndoCount(size - overCount.get());
    }
  }

  private void insertNewRecord() {
    int rowIndex = getSelectedCellRowIndex();
    RecordStrategy prevStrategy = new DeleteRecordStrategy(tableView, rowIndex, controller);
    RecordStrategy newStrategy = new InsertNewRecordStrategy(tableView, rowIndex, controller, null);
    controller.invokeRecord(rowIndex, prevStrategy, newStrategy);
    controller.pushUndoCount(1);
    tableView.getSelectionModel().clearSelection();
    tableView.getSelectionModel().select(rowIndex);
  }

  private void deleteRecord() {
    ObservableList<Integer> indicies = tableView.getSelectionModel().getSelectedIndices();
    int[] newIndicies = UtilInteger.convertDoubleWrapperToPrimitive(indicies);

    AtomicInteger count = new AtomicInteger(0);
    Arrays.stream(newIndicies).forEach(rowIndex -> {
      int row = rowIndex - count.getAndIncrement();
      RecordStrategy prevStrategy = new InsertNewRecordStrategy(tableView, row, controller,
          controller.getRecord(row));
      RecordStrategy newStrategy = new DeleteRecordStrategy(tableView, row, controller);
      controller.invokeRecord(row, prevStrategy, newStrategy);
    });
    controller.pushUndoCount(newIndicies.length);
  }

  int getSelectedCellColumnIndex() {
    return tableView.getSelectionModel().getSelectedCells().get(0).getColumn();
  }

  int getSelectedCellRowIndex() {
    return tableView.getSelectionModel().getSelectedIndex();
  }

  String getSelectedCellValue() {
    @SuppressWarnings("unchecked")
    TablePosition<Skill, String> pos = tableView.getSelectionModel().getSelectedCells().get(0);
    int rowIndex = pos.getRow();
    TableColumn<Skill, String> column = pos.getTableColumn();
    return column.getCellData(rowIndex);
  }

  private double mouseY = 0;
  private double diff = 0;
  private String cellValue;
  private String firstCellValue;
  private static final String NUMBER_REGEX = "^[-]?[0-9]+";
  private Tooltip toolTip = new Tooltip();

  void onMousePressed(MouseEvent event) {
    if (this.isSelected()) {
      mouseY = event.getScreenY();
      cellValue = getSelectedCellValue();
      firstCellValue = getSelectedCellValue();
      toolTip.setText(cellValue);
    }
  }

  void onMouseReleased(MouseEvent event) {
    if (this.isSelected()) {
      diff = 0;
      cellValue = getSelectedCellValue();
      if (!firstCellValue.equals(toolTip.getText())) {
        controller.insertText(toolTip.getText());
        toolTip.setText("");
        if (toolTip.isShowing()) {
          toolTip.hide();
          tableView.setTooltip(null);
        }
      }
    }
  }

  void onMouseDragged(MouseEvent event) {
    if (this.isSelected()) {
      if (cellValue.matches(NUMBER_REGEX)) {
        diff = mouseY - event.getScreenY();
        if (diff % 10 == 0) {
          int numValue = Integer.parseInt(cellValue);

          // @formatter:off
          numValue =
              event.isControlDown() ? 0 < diff ? numValue + 5 : numValue - 5
              : event.isShiftDown() ? 0 < diff ? numValue + 10 : numValue - 10
                                    : 0 < diff ? numValue + 1 : numValue - 1;
          // @formatter:on

          toolTip.setText("" + numValue);

          tableView.setTooltip(toolTip);
          if (!toolTip.isShowing()) {
            toolTip.setX(event.getScreenX());
            toolTip.setY(event.getScreenY());
            toolTip.show(tableView.getScene().getWindow());
          }
          mouseY = event.getScreenY();
          cellValue = "" + numValue;
        }
      }
    }
  }
}
