package application.tableview;

// import 宣言 {{{

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.MainController;
import application.tableview.cell.BooleanTableCell;
import application.tableview.cell.IconTableCell;
import application.tableview.cell.TextAreaTableCell;
import application.tableview.command.ICommand;
import application.tableview.icon.IconIndexChooser;
import application.tableview.input.NumberInputStage;
import application.tableview.strategy.cell.AnimationIdColumnStrategy;
import application.tableview.strategy.cell.ColumnStrategy;
import application.tableview.strategy.cell.CriticalColumnStrategy;
import application.tableview.strategy.cell.DamageElementColumnStrategy;
import application.tableview.strategy.cell.DamageTypeColumnStrategy;
import application.tableview.strategy.cell.DescriptionColumnStrategy;
import application.tableview.strategy.cell.EffectsColumnStrategy;
import application.tableview.strategy.cell.FormulaColumnStrategy;
import application.tableview.strategy.cell.HitTypeColumnStrategy;
import application.tableview.strategy.cell.IconIndexColumnStrategy;
import application.tableview.strategy.cell.IdColumnStrategy;
import application.tableview.strategy.cell.Message1ColumnStrategy;
import application.tableview.strategy.cell.Message2ColumnStrategy;
import application.tableview.strategy.cell.MpCostColumnStrategy;
import application.tableview.strategy.cell.NameColumnStrategy;
import application.tableview.strategy.cell.NoteColumnStrategy;
import application.tableview.strategy.cell.OccasionColumnStrategy;
import application.tableview.strategy.cell.RepeatsColumnStrategy;
import application.tableview.strategy.cell.RequiredWtypeId1ColumnStrategy;
import application.tableview.strategy.cell.RequiredWtypeId2ColumnStrategy;
import application.tableview.strategy.cell.ScopeColumnStrategy;
import application.tableview.strategy.cell.SpeedColumnStrategy;
import application.tableview.strategy.cell.StypeIdColumnStrategy;
import application.tableview.strategy.cell.SuccessRateColumnStrategy;
import application.tableview.strategy.cell.TableCellUpdateCommand;
import application.tableview.strategy.cell.TpCostColumnStrategy;
import application.tableview.strategy.cell.TpGainColumnStrategy;
import application.tableview.strategy.cell.VarianceColumnStrategy;
import application.tableview.strategy.record.RecordStrategy;
import application.tableview.strategy.record.TableRecordUpdateCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DefaultStringConverter;
import util.MyLogger;
import util.UtilIconImage;
import util.UtilTableView;
import util.dictionary.SkillCritical;
import util.dictionary.SkillDamageType;
import util.dictionary.SkillHitType;
import util.dictionary.SkillMessage;
import util.dictionary.SkillOccasion;
import util.dictionary.SkillScope;
import util.json.JsonEffects;
import util.json.JsonSkill;
import util.json.UtilJson;

//}}}

public class SkillTableViewBorderPaneController {
  private TableViewManager leftManager;
  private TableViewManager rightManager;

  private MainController mainController;

  private File iconFile;
  private static ObservableList<String> stypeItems;
  private static ObservableList<String> weaponItems;
  private static ObservableList<String> animationItems;
  private static ObservableList<String> elementItems;

  // FXML コンポーネント//{{{

  @FXML private SplitPane tableViewSplitPane;
  @FXML private TableView<Skill> leftTableView;
  @FXML private TableColumn<Skill, String> leftIdColumn = new TableColumn<>("ID");
  @FXML private TableColumn<Skill, String> leftNameColumn = new TableColumn<>("名前");
  @FXML private TableColumn<Skill, String> leftIconIndexColumn = new TableColumn<>("アイコン");

  @FXML private TableView<Skill> rightTableView = new TableView<>();
  @FXML private TableColumn<Skill, String> idColumn = new TableColumn<>("ID");
  @FXML private TableColumn<Skill, String> nameColumn = new TableColumn<>("名前");
  @FXML private TableColumn<Skill, String> iconIndexColumn = new TableColumn<>("アイコン");
  @FXML private TableColumn<Skill, String> descriptionColumn = new TableColumn<>("説明");
  @FXML private TableColumn<Skill, String> stypeIdColumn = new TableColumn<>("スキルタイプ");
  @FXML private TableColumn<Skill, String> scopeColumn = new TableColumn<>("範囲");
  @FXML private TableColumn<Skill, String> mpCostColumn = new TableColumn<>("消費MP");
  @FXML private TableColumn<Skill, String> tpCostColumn = new TableColumn<>("消費TP");
  @FXML private TableColumn<Skill, String> occasionColumn = new TableColumn<>("使用可能時");
  @FXML private TableColumn<Skill, String> speedColumn = new TableColumn<>("速度補正");
  @FXML private TableColumn<Skill, String> successRateColumn = new TableColumn<>("成功率");
  @FXML private TableColumn<Skill, String> repeatsColumn = new TableColumn<>("連続回数");
  @FXML private TableColumn<Skill, String> tpGainColumn = new TableColumn<>("得TP");
  @FXML private TableColumn<Skill, String> hitTypeColumn = new TableColumn<>("命中タイプ");
  @FXML private TableColumn<Skill, String> animationIdColumn = new TableColumn<>("アニメーション");
  @FXML private TableColumn<Skill, String> message1Column = new TableColumn<>("メッセージ1");
  @FXML private TableColumn<Skill, String> message2Column = new TableColumn<>("メッセージ2");
  @FXML private TableColumn<Skill, String> requiredWtypeId1Column = new TableColumn<>("必要武器1");
  @FXML private TableColumn<Skill, String> requiredWtypeId2Column = new TableColumn<>("必要武器2");
  @FXML private TableColumn<Skill, String> damageTypeColumn = new TableColumn<>("ダメージタイプ");
  @FXML private TableColumn<Skill, String> damageElementColumn = new TableColumn<>("属性");
  @FXML private TableColumn<Skill, String> formulaColumn = new TableColumn<>("計算式");
  @FXML private TableColumn<Skill, String> varianceColumn = new TableColumn<>("分散度");
  @FXML private TableColumn<Skill, String> criticalColumn = new TableColumn<>("会心率");
  @FXML private TableColumn<Skill, String> effectsColumn = new TableColumn<>("使用効果");
  @FXML private TableColumn<Skill, String> noteColumn = new TableColumn<>("メモ");

  //}}}

  @FXML private void initialize() {//{{{
    leftManager = new TableViewManager(leftTableView, this, "leftTable");
    rightManager = new TableViewManager(rightTableView, this, "rightTable");

    rightTableView.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.getTableColumn() != null) {
        if (rightManager.isSelected()) {
          updateInsertComboBox(rightManager.getSelectedCellColumnIndex());
        }
      }
    });

    idColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("name"));
    iconIndexColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("iconIndex"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("description"));
    stypeIdColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("stypeId"));
    scopeColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("scope"));
    mpCostColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("mpCost"));
    tpCostColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("tpCost"));
    occasionColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("occasion"));
    speedColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("speed"));
    successRateColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("successRate"));
    repeatsColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("repeats"));
    tpGainColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("tpGain"));
    hitTypeColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("hitType"));
    animationIdColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("animationId"));
    message1Column.setCellValueFactory(new PropertyValueFactory<Skill, String>("message1"));
    message2Column.setCellValueFactory(new PropertyValueFactory<Skill, String>("message2"));
    requiredWtypeId1Column
      .setCellValueFactory(new PropertyValueFactory<Skill, String>("requiredWtypeId1"));
    requiredWtypeId2Column
      .setCellValueFactory(new PropertyValueFactory<Skill, String>("requiredWtypeId2"));
    damageTypeColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("damageType"));
    damageElementColumn
      .setCellValueFactory(new PropertyValueFactory<Skill, String>("damageElement"));
    formulaColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("formula"));
    varianceColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("variance"));
    criticalColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("critical"));
    effectsColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("effects"));
    noteColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("note"));

    leftIdColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("id"));
    leftNameColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("name"));
    leftIconIndexColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("iconIndex"));

    // カラムをクリックしたときにコンボボックスを呼び出す
    descriptionColumn.setCellFactory(TextAreaTableCell.forTableColumn());
    scopeColumn.setCellFactory(col -> new ComboBoxTableCell<>(SkillScope.getObservableList()));
    mpCostColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    tpCostColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    occasionColumn
      .setCellFactory(col -> new ComboBoxTableCell<>(SkillOccasion.getObservableList()));
    speedColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    successRateColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    repeatsColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    tpGainColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    hitTypeColumn.setCellFactory(col -> new ComboBoxTableCell<>(SkillHitType.getObservableList()));
    message1Column.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    message2Column.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    damageTypeColumn
      .setCellFactory(col -> new ComboBoxTableCell<>(SkillDamageType.getObservableList()));
    formulaColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    varianceColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    criticalColumn.setCellFactory(col -> new BooleanTableCell());

    leftNameColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    leftIconIndexColumn.setCellFactory(col -> new IconTableCell());

    leftTableView.setItems(rightTableView.getItems());

    leftIdColumn = idColumn;
    leftNameColumn = nameColumn;
    leftIconIndexColumn = iconIndexColumn;

    rightTableView.getColumns().remove(idColumn);
    rightTableView.getColumns().remove(nameColumn);
    rightTableView.getColumns().remove(iconIndexColumn);
  }//}}}

  @FXML private void leftTableViewOnMouseClicked(MouseEvent event) {//{{{
    if (!leftTableView.getSelectionModel().isEmpty()) {
      rightTableView.getSelectionModel().clearSelection();
      updateSelection();

      if (event.getClickCount() == 2) {
        int columnIndex = leftManager.getSelectedCellColumnIndex();
        ObservableList<TableColumn<Skill, ?>> columns = leftTableView.getColumns();
        if ("leftIconIndexColumn".equals(columns.get(columnIndex).getId())) {
          setIconIndex();
        }
      }
    }
  }//}}}

  private void setIconIndex() {//{{{
    TableViewSelectionModel<Skill> model = leftTableView.getSelectionModel();

    String indexStr = model.getSelectedItem().iconIndexProperty().get();
    int iconIndex = Integer.parseInt(indexStr);
    IconIndexChooser chooser = new IconIndexChooser(iconFile, iconIndex);
    chooser.showAndWait();

    int newIconIndex = chooser.getController().getIconIndex();
    if (iconIndex != newIconIndex) {
      ObservableList<Integer> rowIndices = model.getSelectedIndices();
      rowIndices.stream()
        .forEach(rowIndex -> {
          ColumnStrategy strategy = new IconIndexColumnStrategy(rightTableView, rowIndex);
          ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, 0,
              "" + newIconIndex, strategy);
          mainController.invoke(command);
        });

      mainController.pushUndoCount(rowIndices.size());
    }
  }//}}}

  @FXML private void rightTableViewOnMouseClicked(MouseEvent event) {//{{{
    updateSelection();

    if (!rightTableView.getSelectionModel().isEmpty()) {
      if (event.getClickCount() == 2) {
        int columnIndex = rightManager.getSelectedCellColumnIndex();
        ObservableList<TableColumn<Skill, ?>> columns = rightTableView.getColumns();

        changeCritical(columnIndex, columns);
      }
    }
  }//}}}

  @FXML private void rightTableViewOnMousePressed(MouseEvent event) {
    rightManager.onMousePressed(event);
  }

  @FXML private void rightTableViewOnMouseReleased(MouseEvent event) {
    rightManager.onMouseReleased(event);
  }

  @FXML private void rightTableViewOnMouseDragged(MouseEvent event) {
    rightManager.onMouseDragged(event);
  }

  @FXML private void rightTableViewOnKeyPressed(KeyEvent event) {//{{{
    if (rightManager.isSelected()) {
      if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
        int columnIndex = rightManager.getSelectedCellColumnIndex();
        ObservableList<TableColumn<Skill, ?>> columns = rightTableView.getColumns();

        changeCritical(columnIndex, columns);
      }
    }
  }//}}}

  private void changeCritical(int columnIndex, ObservableList<TableColumn<Skill, ?>> columns) {//{{{
    if (columnIndex == columns.indexOf(criticalColumn)) {
      String text = rightManager.getSelectedCellValue();
      text = "あり".equals(text) ? "なし" : "あり";
      insertText(text);
    }
  }//}}}

  public void updateSelection() {//{{{
    if (rightTableView.isFocused()) {
      leftTableView.getSelectionModel().clearSelection();
      leftTableView.getSelectionModel()
        .select(rightTableView.getFocusModel().getFocusedIndex());
      if (rightManager.isSelected()) {
        int columnIndex = rightManager.getSelectedCellColumnIndex();
        columnIndex += 3;
        int rowIndex = rightManager.getSelectedCellRowIndex();
        updateAxisLabels(columnIndex, rowIndex);
      }
      return;
    }

    // rightTableView.getSelectionModel().clearSelection();
    // rightTableView.getSelectionModel()
    // .select(leftTableView.getFocusModel().getFocusedIndex());

    int columnIndex = leftManager.getSelectedCellColumnIndex();
    int rowIndex = leftManager.getSelectedCellRowIndex();
    updateAxisLabels(columnIndex, rowIndex);
  }//}}}

  /**
   * 選択中のセルにテキストを挿入する。
   * @param newText
   *          新しく挿入するテキスト
   */
  public void insertText(String newText) {//{{{
    if (newText != null) {
      if (rightManager.isSelected()) {
        invokeSelectedCell(rightTableView, newText);
      } else if (leftManager.isSelected()) {
        invokeSelectedCell(leftTableView, newText);
      }
    }
  }//}}}

  /**
   * 選択中のセルを対象にinvoke
   * @param table
   * @param newText
   */
  private void invokeSelectedCell(TableView<Skill> table, String newText) {//{{{
    ObservableList<Integer> rowIndices = table.getSelectionModel().getSelectedIndices();
    for (int rowIndex : rowIndices) {
      invoke(newText, rowIndex);
    }
    mainController.pushUndoCount(rowIndices.size());
  }//}}}

  /**
   * 対象行番号を指定してinvoke。<br>
   * このメソッド単体ではundoCountを更新しない。<br>
   * undoCountの更新が必要な場合はpushUndoCountメソッドを呼び出す必要がある。
   * @param table
   * @param newText
   * @param rowIndex
   */
  public void invoke(String newText, int rowIndex) {//{{{
    ColumnStrategy strategy = getStrategy(rowIndex);
    if (!strategy.isInvokable(newText)) {
      newText = strategy.defaultValue(newText);
      if (newText == null) {
        return;
      }
    }
    ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, 0,
        newText, strategy);
    mainController.invoke(command);
  }//}}}

  /**
   * レコードを対象に実行する操作。<br>
   * このメソッド単体ではundoCountを更新しない。<br>
   * @param rowIndex
   * @param prevStrategy
   * @param newStrategy
   */
  void invokeRecord(int rowIndex, RecordStrategy prevStrategy, RecordStrategy newStrategy) {//{{{
    ICommand command = new TableRecordUpdateCommand(rightTableView, rowIndex, prevStrategy,
        newStrategy);
    mainController.invoke(command);
  }//}}}

  /**
   * アンドゥ回数のスタックにプッシュする。
   * このメソッドはTableViewManagerから呼び出されるために存在する。
   * @param count undo回数
   */
  void pushUndoCount(int count) {
    mainController.pushUndoCount(count);
  }

  /**
   * カラムインデックスを記述したプロパティファイルを出力する。
   */
  public void exportPropertiesFile() {//{{{
    leftManager.outputPropertiesFile();
    rightTableView.getColumns().add(idColumn);
    rightTableView.getColumns().add(nameColumn);
    rightTableView.getColumns().add(iconIndexColumn);
    rightManager.outputPropertiesFile();
  }//}}}

  /**
   * 前のセルに選択を移す.
   */
  public void movePrevious() {//{{{
    if (rightManager.isSelected()) {
      rightManager.movePrevious();
    } else if (leftManager.isSelected()) {
      leftManager.movePrevious();
    }
  }//}}}

  /**
   * 次のセルに選択を移す.
   */
  public void moveNext() {//{{{
    if (rightManager.isSelected()) {
      rightManager.moveNext();
    } else if (leftManager.isSelected()) {
      leftManager.moveNext();
    }
  }//}}}

  public void changeMaxRecords() {//{{{
    int recordsCount = rightTableView.getItems().size();
    NumberInputStage inputStage = new NumberInputStage(recordsCount);
    inputStage.showAndWait();
    int newRecordsCount = inputStage.getValue();
    if (0 < newRecordsCount && newRecordsCount <= 2000) {
      if (newRecordsCount < recordsCount) {
        int diff = recordsCount - newRecordsCount;
        IntStream.range(0, diff)
          .forEach(i -> {
            rightManager.deleteRecord(newRecordsCount);
          });
        pushUndoCount(diff);
        return;
      }
      int diff = newRecordsCount - recordsCount;
      IntStream.range(0, diff)
        .forEach(i -> {
          rightManager.insertNewRecord(recordsCount);
        });
      pushUndoCount(diff);
      return;
    }
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("数値が不正です。");
    alert.setContentText("数値は1以上2,000以下の範囲で入力してください。");
    alert.showAndWait();
  }//}}}

  private MyLogger logger = new MyLogger(getClass().getName());

  public List<JsonSkill> makeSkillData() {//{{{
    int size = rightTableView.getItems().size();

    List<JsonSkill> skillList = new ArrayList<>();
    skillList.add(null);
    for (int index = 0; index < size; index++) {
      Skill record = getRecord(index);
      try {
        JsonSkill data = new JsonSkill(record, stypeItems, animationItems, weaponItems,
            elementItems);
        skillList.add(data);
      } catch (NumberFormatException e) {
        logger.log(Level.SEVERE, "ファイル出力: 数値変換エラー", e);
        showAlert();
        return null;
      } catch (JsonProcessingException e) {
        logger.log(Level.SEVERE, "ファイル出力: Json変換エラー", e);
        showAlert();
        return null;
      } catch (IOException e) {
        logger.log(Level.SEVERE, "ファイル出力: アウトプットエラー", e);
        showAlert();
        return null;
      }
    }
    return skillList;
  }//}}}

  private void showAlert() {//{{{
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText("ファイル出力エラー");
    alert.setContentText("正常にファイルを保存できませんでした。" + System.getProperty("line.separator") +
        "logフォルダのエラーを確認してください。");
    alert.showAndWait();
  }//}}}

  public void updateId() {//{{{
    int size = rightTableView.getItems().size();
    IntStream.range(0, size)
      .forEach(index -> {
        String id = String.format("%04d", index + 1);
        rightTableView.getItems().get(index).idProperty().set(id);
      });
  }//}}}

  /**
   * 選択行の使用効果タブのセルのテキストを読み取り、
   * 使用効果プレビューを更新する。
   */
  public void updateEffectsPane() {//{{{
    if (rightManager.isSelected() || leftManager.isSelected()) {
      int rowIndex = rightManager.isSelected() ? rightTableView.getFocusModel().getFocusedIndex()
        : leftTableView.getFocusModel().getFocusedIndex();
      String effectsText = rightTableView.getItems().get(rowIndex).effectsProperty().get();
      int size = rightTableView.getItems().size();
      ArrayList<String> skillsList = new ArrayList<>(size);
      skillsList.add(null);
      rightTableView.getItems()
        .forEach(item -> {
          skillsList.add(item.nameProperty().get());
        });
      mainController.updateEffectsTableView(effectsText, skillsList);
    }
  }//}}}

  /**
   * テキストをそのままセルに上書きする。
   * @param newText
   */
  public void updateEffectsCell(String newText) {//{{{
    int rowIndex = rightTableView.getFocusModel().getFocusedIndex();
    int columnIndex = rightTableView.getColumns().indexOf(effectsColumn);
    ColumnStrategy strategy = new EffectsColumnStrategy(rightTableView, rowIndex, this);

    ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, columnIndex,
        newText, strategy);
    mainController.invoke(command);
    mainController.pushUndoCount(1);
  }//}}}

  /**
   * indexの位置に存在する要素をvaluesで上書きする。
   * この処理は１レコードを対象とする場合にのみ使用する。
   * @param selectedIndex
   * @param values
   */
  public void updateEffectsCell(int selectedIndex, double[] values) {
    updateEffectsCellNonPushUndo(selectedIndex, values);
    mainController.pushUndoCount(1);
  }

  /**
   * indexの位置に存在する要素をvaluesで上書きする。
   * @param selectedIndex
   * @param values
   */
  public void updateEffectsCellNonPushUndo(int selectedIndex, double[] values) {//{{{
    String effectsText = getSelectedEffects();
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(effectsText);
      int size = root.size();
      List<String> textList = new ArrayList<>(size);

      IntStream.range(0, size)
        .forEach(i -> {
          String text = i == selectedIndex ? convertJsonText(values) : root.get(i).toString();
          textList.add(text);
        });
      if (size == selectedIndex) {
        textList.add(convertJsonText(values));
      }
      String result = "[" + String.join(",", textList) + "]";

      int rowIndex = leftTableView.getFocusModel().getFocusedIndex();
      int columnIndex = rightTableView.getColumns().indexOf(effectsColumn);
      ColumnStrategy strategy = new EffectsColumnStrategy(rightTableView, rowIndex, this);

      ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, columnIndex,
          result, strategy);
      mainController.invoke(command);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }//}}}

  void updateAxisLabels(int columnIndex, int rowIndex) {
    mainController.updateAxisLabels(columnIndex, rowIndex);
  }

  void updateInsertComboBox(int columnIndex) {//{{{
    ComboBox<String> insertComboBox = mainController.getInsertComboBox();
    insertComboBox.setDisable(false);
    if (rightManager.isSelected()) {
      if (columnIndex == rightTableView.getColumns().indexOf(stypeIdColumn)) {
        insertComboBox.setItems(stypeItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(scopeColumn)) {
        setItems(insertComboBox, SkillScope.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(occasionColumn)) {
        setItems(insertComboBox, SkillOccasion.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(hitTypeColumn)) {
        setItems(insertComboBox, SkillHitType.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(animationIdColumn)) {
        insertComboBox.setItems(animationItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(message1Column)) {
        setItems(insertComboBox, SkillMessage.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(message2Column)) {
        setItems(insertComboBox, SkillMessage.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageTypeColumn)) {
        setItems(insertComboBox, SkillDamageType.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(criticalColumn)) {
        setItems(insertComboBox, SkillCritical.getObservableList());
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId1Column)) {
        setItems(insertComboBox, weaponItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId2Column)) {
        setItems(insertComboBox, weaponItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageElementColumn)) {
        setItems(insertComboBox, elementItems);
      } else {
        insertComboBox.setDisable(true);
      }
    }
  }//}}}

  void updateNotePane() {//{{{
    if (rightManager.isSelected() || leftManager.isSelected()) {
      int selectedIndex = rightManager.isSelected()
        ? rightTableView.getFocusModel().getFocusedIndex()
        : leftTableView.getFocusModel().getFocusedIndex();

      String note = rightTableView.getItems().get(selectedIndex).noteProperty().get();
      mainController.setNoteText(note);
    }
  }//}}}

  void normalInsert() {
    if (rightManager.isSelected() || leftManager.isSelected()) {
      mainController.normalInsert();
    }
  }

  void topInsert() {
    if (rightManager.isSelected() || leftManager.isSelected()) {
      mainController.topInsert();
    }
  }

  void endInsert() {
    if (rightManager.isSelected() || leftManager.isSelected()) {
      mainController.endInsert();
    }
  }

  void changeDisablePreviews(boolean b) {
    mainController.changeDisablePreviews(b);
  }

  private void setItems(ComboBox<String> comboBox, ObservableList<String> items) {//{{{
    if (!Objects.equals(comboBox.getItems(), items)) {
      comboBox.setItems(items);
    }
  }//}}}

  private String convertJsonText(double[] values) {//{{{
    ObjectMapper mapper = new ObjectMapper();
    JsonEffects effect = new JsonEffects(values);
    try {
      return mapper.writeValueAsString(effect);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }//}}}

  public boolean isSelected() {
    return (rightManager.isSelected() || leftManager.isSelected());
  }

  public String getselectedCellValue() {//{{{
    if (rightManager.isSelected()) {
      return rightManager.getSelectedCellValue();
    }
    if (leftManager.isSelected()) {
      return leftManager.getSelectedCellValue();
    }
    return "";
  }//}}}

  public List<String> getselectedCellValues() {//{{{
    if (rightManager.isSelected()) {
      return rightManager.getSelectedCellValues();
    }
    if (leftManager.isSelected()) {
      return leftManager.getSelectedCellValues();
    }
    return null;
  }//}}}

  public List<Integer> getSelectedRowIndices() {//{{{
    if (rightManager.isSelected()) {
      return rightManager.getSelectedCellRowIndicies();
    }
    if (leftManager.isSelected()) {
      return leftManager.getSelectedCellRowIndicies();
    }
    return null;
  }//}}}

  public String getNormalAttackText() {
    return rightTableView.getItems().get(0).nameProperty().get();
  }

  public String getSelectedEffects() {//{{{
    if (rightManager.isSelected()) {
      return rightTableView.getSelectionModel().getSelectedItem().effectsProperty().get();
    }
    int select = leftManager.getSelectedCellRowIndex();
    return leftTableView.getItems().get(select).effectsProperty().get();
  }//}}}

  public SplitPane getSplitPane() {
    return tableViewSplitPane;
  }

  Skill getRecord(int rowIndex) {
    return UtilTableView.getSkillRecord(rightTableView, rowIndex);
  }

  /**
   * 選択中のセル位置によってカラム戦略クラスを変更する。
   */
  private ColumnStrategy getStrategy(int rowIndex) {//{{{
    if (rightManager.isSelected()) {
      int columnIndex = rightManager.getSelectedCellColumnIndex();
      if (columnIndex == rightTableView.getColumns().indexOf(descriptionColumn)) {
        return new DescriptionColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(stypeIdColumn)) {
        return new StypeIdColumnStrategy(rightTableView, rowIndex, stypeItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(scopeColumn)) {
        return new ScopeColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(mpCostColumn)) {
        return new MpCostColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(tpCostColumn)) {
        return new TpCostColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(occasionColumn)) {
        return new OccasionColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(speedColumn)) {
        return new SpeedColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(successRateColumn)) {
        return new SuccessRateColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(repeatsColumn)) {
        return new RepeatsColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(tpGainColumn)) {
        return new TpGainColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(hitTypeColumn)) {
        return new HitTypeColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(animationIdColumn)) {
        return new AnimationIdColumnStrategy(rightTableView, rowIndex, animationItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(message1Column)) {
        return new Message1ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(message2Column)) {
        return new Message2ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId1Column)) {
        return new RequiredWtypeId1ColumnStrategy(rightTableView, rowIndex, weaponItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId2Column)) {
        return new RequiredWtypeId2ColumnStrategy(rightTableView, rowIndex, weaponItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageTypeColumn)) {
        return new DamageTypeColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageElementColumn)) {
        return new DamageElementColumnStrategy(rightTableView, rowIndex, elementItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(formulaColumn)) {
        return new FormulaColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(varianceColumn)) {
        return new VarianceColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(criticalColumn)) {
        return new CriticalColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(effectsColumn)) {
        return new EffectsColumnStrategy(rightTableView, rowIndex, this);
      } else if (columnIndex == rightTableView.getColumns().indexOf(noteColumn)) {
        return new NoteColumnStrategy(rightTableView, rowIndex);
      }
    } else if (leftManager.isSelected()) {
      int columnIndex = leftManager.getSelectedCellColumnIndex();
      if ("leftIdColumn".equals(leftTableView.getColumns().get(columnIndex).getId())) {
        return new IdColumnStrategy(rightTableView, rowIndex);
      } else if ("leftNameColumn"
          .equals(leftTableView.getColumns().get(columnIndex).getId())) {
        return new NameColumnStrategy(rightTableView, rowIndex);
      } else if ("leftIconIndexColumn"
          .equals(leftTableView.getColumns().get(columnIndex).getId())) {
        return new IconIndexColumnStrategy(rightTableView, rowIndex);
          }
    }
    return null;
  }//}}}

  /**
   * ファイルからjsonデータを取り出してテーブルビューに追加する。
   * @param file
   *          jsonファイル
   */
  public void setSkillDatas(File skillsFile, File systemFile, File animationFile) {//{{{
    rightTableView.getSelectionModel().clearSelection();
    leftTableView.getSelectionModel().clearSelection();
    rightTableView.getItems().clear();
    leftTableView.getItems().clear();

    List<String> skillTypeList = UtilJson.makeDataList(systemFile, "skillTypes", "なし");
    stypeItems = FXCollections.observableArrayList(skillTypeList);
    stypeIdColumn.setCellFactory(col -> new ComboBoxTableCell<>(stypeItems));

    List<String> weaponList = UtilJson.makeDataList(systemFile, "weaponTypes", "なし");
    weaponItems = FXCollections.observableArrayList(weaponList);
    requiredWtypeId1Column.setCellFactory(col -> new ComboBoxTableCell<>(weaponItems));
    requiredWtypeId2Column.setCellFactory(col -> new ComboBoxTableCell<>(weaponItems));

    List<String> tmpElementList = UtilJson.makeDataList(systemFile, "elements", "なし");
    List<String> elementList = new ArrayList<>(tmpElementList.size() + 1);
    elementList.add("-1: 通常攻撃");
    elementList.addAll(tmpElementList);
    elementItems = FXCollections.observableArrayList(elementList);
    damageElementColumn.setCellFactory(col -> new ComboBoxTableCell<>(elementItems));

    List<String> animationList = UtilJson.makeAnimationList(animationFile);
    animationItems = FXCollections.observableArrayList(animationList);
    animationIdColumn.setCellFactory(col -> new ComboBoxTableCell<>(animationItems));

    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode skillsRoot = mapper.readTree(skillsFile);
      IntStream.range(1, skillsRoot.size())
        .forEach(index -> {
          JsonNode children = skillsRoot.get(index);
          rightTableView.getItems()
            .add(UtilJson.makeSkillRecord(children, skillTypeList, animationList, weaponList,
                  elementList));
        });
      updateEffectsPane();
      updateNotePane();
      UtilTableView.bindScrollBar(leftTableView, rightTableView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }//}}}

  public void setMainController(MainController aMainController) {
    mainController = aMainController;
  }

  /**
   * メモ欄を更新する。
   * @param text
   */
  public void setNote(String text) {
    if (!rightTableView.getSelectionModel().isEmpty()) {
      int selectedIndex = rightTableView.getSelectionModel().getSelectedIndex();
      rightTableView.getItems().get(selectedIndex).noteProperty().set(text);
    }
  }

  public void setIconFile(File iconFile) {
    this.iconFile = iconFile;
    IconTableCell.setIconImageList(UtilIconImage.makeIconImageList(iconFile));
  }

  public void setTableViewFontSize(int tableViewFontSize) {
    rightTableView.setStyle(String.format("-fx-font-size:%dpt;", tableViewFontSize));
    leftTableView.setStyle(String.format("-fx-font-size:%dpt;", tableViewFontSize));
  }

  public void setTableCellSize(int tableCellHeight) {
    rightTableView.setFixedCellSize(tableCellHeight);
    leftTableView.setFixedCellSize(tableCellHeight);
  }
}
