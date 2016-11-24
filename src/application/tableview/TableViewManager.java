package application.tableview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jiro.lib.java.util.PropertiesHundler;

public class TableViewManager {
  private PropertiesHundler columnIndexProp;
  private PropertiesHundler columnWidthProp;

  private TableView<Skill> tableView;
  private SkillTableViewBorderPaneController controller;

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
          if (newValue != null) {
            String id = newValue.idProperty().get();
            String name = newValue.nameProperty().get();
            controller.updateHeader(id, name);
          }
        });

    tableView.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.getTableColumn() != null) {
        int columnIndex = newVal.getColumn();
        int rowIndex = tableView.getSelectionModel().getSelectedIndex();
        controller.updateAxisLabels(columnIndex, rowIndex);
        controller.updateInsertComboBox(columnIndex);
      }
    });

    tableView.getColumns().forEach(c -> settingTableColumn((TableColumn<Skill, String>) c));
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

  int getSelectedCellColumnIndex() {
    return tableView.getSelectionModel().getSelectedCells().get(0).getColumn();
  }

  int getSelectedCellRowIndex() {
    return tableView.getSelectionModel().getSelectedIndex();
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
}
