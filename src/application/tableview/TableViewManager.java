package application.tableview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.IntStream;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import jiro.lib.java.util.PropertiesHundler;

public class TableViewManager {
  private PropertiesHundler columnIndexProp;
  private PropertiesHundler columnWidthProp;

  private TableView<Skill> tableView;
  private SkillTableViewBorderPaneController controller;

  private static final String COLUMN_SEPARATOR = ",";
  private static final String ROW_SEPARATOR = "/";

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

    MenuItem copyItem = new MenuItem("選択中のセルをコピー");
    copyItem.setOnAction(e -> copyValueOfSelectedCells());
    MenuItem pasteItem = new MenuItem("選択中のセルから貼り付け");
    pasteItem.setOnAction(e -> pasteValue());

    ContextMenu menu = new ContextMenu(copyItem, pasteItem);
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

  void copyValueOfSelectedCells() {
    ObservableList<Integer> indicies = tableView.getSelectionModel().getSelectedIndices();
    List<String> textList = new ArrayList<>(indicies.size());
    for (int index : indicies) {
      @SuppressWarnings("unchecked")
      TablePosition<Skill, String> pos = tableView.getSelectionModel().getSelectedCells().get(0);
      TableColumn<Skill, String> column = pos.getTableColumn();
      String text = column.getCellData(index);
      text = text.replaceAll(ROW_SEPARATOR, "\\\\" + ROW_SEPARATOR);
      textList.add(text);
    }

    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent content = new ClipboardContent();
    content.putString(String.join(ROW_SEPARATOR, textList));
    clipboard.setContent(content);
  }

  void pasteValue() {
    // 実装途中
    Clipboard clipboard = Clipboard.getSystemClipboard();
    String text = clipboard.getString();
    String[] texts = text.split(ROW_SEPARATOR);

    LinkedList<String> linkedList = new LinkedList<>();
    Arrays.stream(texts).forEach(s -> linkedList.offer(s));
    linkedList.stream().forEach(System.out::println);

    List<String> newList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    while (!linkedList.isEmpty()) {
      String string = linkedList.poll();
      sb.append(string);
      if (string.matches(".*\\\\$")) {
        sb.append(ROW_SEPARATOR);
        continue;
      }
      newList.add(sb.toString());
      sb.setLength(0);
    }

    newList.stream().forEach(System.out::println);
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
        if (diff % 7 == 0) {
          int numValue = Integer.parseInt(cellValue);

          numValue = 0 < diff ? numValue + 1 : numValue - 1;
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
