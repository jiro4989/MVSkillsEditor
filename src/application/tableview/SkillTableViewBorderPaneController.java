package application.tableview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.MainController;
import application.tableview.command.ICommand;
import application.tableview.command.TableCellUpdateCommand;
import application.tableview.icon.IconIndexChooser;
import application.tableview.strategy.AnimationIdColumnStrategy;
import application.tableview.strategy.ColumnStrategy;
import application.tableview.strategy.CriticalColumnStrategy;
import application.tableview.strategy.DamageElementColumnStrategy;
import application.tableview.strategy.DamageTypeColumnStrategy;
import application.tableview.strategy.DescriptionColumnStrategy;
import application.tableview.strategy.EffectsColumnStrategy;
import application.tableview.strategy.FormulaColumnStrategy;
import application.tableview.strategy.HitTypeColumnStrategy;
import application.tableview.strategy.IconIndexColumnStrategy;
import application.tableview.strategy.IdColumnStrategy;
import application.tableview.strategy.Message1ColumnStrategy;
import application.tableview.strategy.Message2ColumnStrategy;
import application.tableview.strategy.MpCostColumnStrategy;
import application.tableview.strategy.NameColumnStrategy;
import application.tableview.strategy.NoteColumnStrategy;
import application.tableview.strategy.OccasionColumnStrategy;
import application.tableview.strategy.RepeatsColumnStrategy;
import application.tableview.strategy.RequiredWtypeId1ColumnStrategy;
import application.tableview.strategy.RequiredWtypeId2ColumnStrategy;
import application.tableview.strategy.ScopeColumnStrategy;
import application.tableview.strategy.SpeedColumnStrategy;
import application.tableview.strategy.StypeIdColumnStrategy;
import application.tableview.strategy.SuccessRateColumnStrategy;
import application.tableview.strategy.TpCostColumnStrategy;
import application.tableview.strategy.TpGainColumnStrategy;
import application.tableview.strategy.VarianceColumnStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.converter.DefaultStringConverter;
import jiro.lib.java.util.PropertiesHundler;
import util.MyLogger;
import util.UtilIconImage;
import util.UtilJson;
import util.UtilTableView;
import util.dictionary.SkillScope;

public class SkillTableViewBorderPaneController {
  private TableViewManager leftManager;
  private TableViewManager rightManager;

  private MainController mainController;
  private PropertiesHundler prop = new PropertiesHundler("column-indices");
  private static final String[] INDICES_KEYS = {
      "id-index", "name-index", "iconIndex-index", "description-index", "stypeId-index",
      "scope-index", "mpCost-index", "tpCost-index", "occasion-index", "speed-index",
      "successRate-index", "repeats-index", "tpGain-index", "hitType-index", "animationId-index",
      "message1-index", "message2-index", "requiredWtypeId1-index", "requiredWtypeId2-index",
      "damageType-index", "damageElementId-index", "formula-index", "variance-index",
      "critical-index", "effects-index", "note-index",
  };

  private File iconFile;
  private List<String> skillTypeList;
  private ObservableList<String> scopeItems = FXCollections
      .observableArrayList(SkillScope.getNameList());
  /**
   * 各カラムのインデックス配列
   */
  private int[] columnIndices = new int[INDICES_KEYS.length];

  @FXML private Label idLabel;
  @FXML private TextField nameTextField;
  @FXML private HBox hBox;
  @FXML private ComboBox<String> insertComboBox;

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

  @FXML
  private void initialize() {
    leftManager = new TableViewManager(leftTableView, this);
    rightManager = new TableViewManager(rightTableView, this);

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

    // 各種テーブルカラムのカスタマイズ
    descriptionColumn.setCellFactory(TextAreaTableCell.forTableColumn());
    scopeColumn.setCellFactory(col -> new ComboBoxTableCell<>(scopeItems));

    leftNameColumn.setCellFactory(col -> new TextFieldTableCell<>(new DefaultStringConverter()));
    leftIconIndexColumn.setCellFactory(col -> new IconTableCell());

    initializeColumnPosition();

    insertComboBox.itemsProperty().addListener((obs, oldVal, newVal) -> {
      insertComboBox.getSelectionModel().select(0);
    });
    insertComboBox.setOnAction(e -> {
      insertText(insertComboBox.getValue());
    });

    leftTableView.setItems(rightTableView.getItems());

    leftIdColumn = idColumn;
    leftNameColumn = nameColumn;
    leftIconIndexColumn = iconIndexColumn;

  }

  /**
   * プロパティファイルからカラムインデックスを読み取り、カラム位置を設定する。
   */
  private void initializeColumnPosition() {
    if (prop.exists()) {
      prop.load();
      rightTableView.getColumns().clear();

      IntStream.range(0, columnIndices.length).forEach(i -> {
        columnIndices[i] = Integer.parseInt(prop.getValue(INDICES_KEYS[i]));
      });

      for (int i = 0; i < INDICES_KEYS.length; i++) {
        if (i == columnIndices[0]) {
          rightTableView.getColumns().add(i, idColumn);
        } else if (i == columnIndices[1]) {
          rightTableView.getColumns().add(i, nameColumn);
        } else if (i == columnIndices[2]) {
          rightTableView.getColumns().add(i, iconIndexColumn);
        } else if (i == columnIndices[3]) {
          rightTableView.getColumns().add(i, descriptionColumn);
        } else if (i == columnIndices[4]) {
          rightTableView.getColumns().add(i, stypeIdColumn);
        } else if (i == columnIndices[5]) {
          rightTableView.getColumns().add(i, scopeColumn);
        } else if (i == columnIndices[6]) {
          rightTableView.getColumns().add(i, mpCostColumn);
        } else if (i == columnIndices[7]) {
          rightTableView.getColumns().add(i, tpCostColumn);
        } else if (i == columnIndices[8]) {
          rightTableView.getColumns().add(i, occasionColumn);
        } else if (i == columnIndices[9]) {
          rightTableView.getColumns().add(i, speedColumn);
        } else if (i == columnIndices[10]) {
          rightTableView.getColumns().add(i, successRateColumn);
        } else if (i == columnIndices[11]) {
          rightTableView.getColumns().add(i, repeatsColumn);
        } else if (i == columnIndices[12]) {
          rightTableView.getColumns().add(i, tpGainColumn);
        } else if (i == columnIndices[13]) {
          rightTableView.getColumns().add(i, hitTypeColumn);
        } else if (i == columnIndices[14]) {
          rightTableView.getColumns().add(i, animationIdColumn);
        } else if (i == columnIndices[15]) {
          rightTableView.getColumns().add(i, message1Column);
        } else if (i == columnIndices[16]) {
          rightTableView.getColumns().add(i, message2Column);
        } else if (i == columnIndices[17]) {
          rightTableView.getColumns().add(i, requiredWtypeId1Column);
        } else if (i == columnIndices[18]) {
          rightTableView.getColumns().add(i, requiredWtypeId2Column);
        } else if (i == columnIndices[19]) {
          rightTableView.getColumns().add(i, damageTypeColumn);
        } else if (i == columnIndices[20]) {
          rightTableView.getColumns().add(i, damageElementColumn);
        } else if (i == columnIndices[21]) {
          rightTableView.getColumns().add(i, formulaColumn);
        } else if (i == columnIndices[22]) {
          rightTableView.getColumns().add(i, varianceColumn);
        } else if (i == columnIndices[23]) {
          rightTableView.getColumns().add(i, criticalColumn);
        } else if (i == columnIndices[24]) {
          rightTableView.getColumns().add(i, effectsColumn);
        } else if (i == columnIndices[25]) {
          rightTableView.getColumns().add(i, noteColumn);
        }
      }
    }
  }

  @FXML
  private void leftSkillTableViewOnMouseClicked(MouseEvent event) {
    rightTableView.getSelectionModel().clearSelection();

    if (!leftTableView.getSelectionModel().isEmpty()) {
      if (event.getClickCount() == 2) {
        int columnIndex = leftManager.getSelectedCellColumnIndex();
        ObservableList<TableColumn<Skill, ?>> columns = leftTableView.getColumns();
        if ("leftIconIndexColumn".equals(columns.get(columnIndex).getId())) {
          setIconIndex();
        }
      }
    }
  }

  private void setIconIndex() {
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
            ColumnStrategy strategy = getStrategy(rowIndex);
            ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, 0,
                "" + newIconIndex, strategy);
            mainController.invoke(command);
          });

      mainController.pushUndoCount(rowIndices.size());
    }
  }

  @FXML
  private void skillTableViewOnMouseClicked(MouseEvent event) {
    leftTableView.getSelectionModel().clearSelection();

    if (!rightTableView.getSelectionModel().isEmpty()) {
      if (event.getClickCount() == 2) {
        int columnIndex = rightTableView.getSelectionModel().getSelectedCells().get(0).getColumn();
        ObservableList<TableColumn<Skill, ?>> columns = rightTableView.getColumns();

        if (columnIndex == columns.indexOf(leftIconIndexColumn)) {
          String indexStr = rightTableView.getSelectionModel().getSelectedItem().iconIndexProperty()
              .get();
          int iconIndex = Integer.parseInt(indexStr);
          IconIndexChooser chooser = new IconIndexChooser(iconFile, iconIndex);
          chooser.showAndWait();

          int newIconIndex = chooser.getController().getIconIndex();
          if (iconIndex != newIconIndex) {
            insertText("" + newIconIndex);
          }
        } else if (columnIndex == columns.indexOf(scopeColumn)) {
        }
      }
    }
  }

  /**
   * 選択中のセルにテキストを挿入する。
   * @param newText
   *          新しく挿入するテキスト
   */
  public void insertText(String newText) {
    if (leftManager.isSelected()) {
      invoke(leftTableView, newText);
    } else if (rightManager.isSelected()) {
      invoke(rightTableView, newText);
    }
  }

  private void invoke(TableView<Skill> table, String newText) {
    ObservableList<Integer> rowIndices = table.getSelectionModel().getSelectedIndices();
    for (int rowIndex : rowIndices) {
      ColumnStrategy strategy = getStrategy(rowIndex);
      if (!strategy.isInvokable(newText)) {
        return;
      }
      ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, 0,
          newText, strategy);
      mainController.invoke(command);
    }

    mainController.pushUndoCount(rowIndices.size());
  }

  /**
   * カラムインデックスを記述したプロパティファイルを出力する。
   */
  public void outputPropertiesFile() {
    prop.setValue(INDICES_KEYS[0], "" + rightTableView.getColumns().indexOf(idColumn));
    prop.setValue(INDICES_KEYS[1], "" + rightTableView.getColumns().indexOf(nameColumn));
    prop.setValue(INDICES_KEYS[2], "" + rightTableView.getColumns().indexOf(iconIndexColumn));
    prop.setValue(INDICES_KEYS[3], "" + rightTableView.getColumns().indexOf(descriptionColumn));
    prop.setValue(INDICES_KEYS[4], "" + rightTableView.getColumns().indexOf(stypeIdColumn));
    prop.setValue(INDICES_KEYS[5], "" + rightTableView.getColumns().indexOf(scopeColumn));
    prop.setValue(INDICES_KEYS[6], "" + rightTableView.getColumns().indexOf(mpCostColumn));
    prop.setValue(INDICES_KEYS[7], "" + rightTableView.getColumns().indexOf(tpCostColumn));
    prop.setValue(INDICES_KEYS[8], "" + rightTableView.getColumns().indexOf(occasionColumn));
    prop.setValue(INDICES_KEYS[9], "" + rightTableView.getColumns().indexOf(speedColumn));
    prop.setValue(INDICES_KEYS[10], "" + rightTableView.getColumns().indexOf(successRateColumn));
    prop.setValue(INDICES_KEYS[11], "" + rightTableView.getColumns().indexOf(repeatsColumn));
    prop.setValue(INDICES_KEYS[12], "" + rightTableView.getColumns().indexOf(tpGainColumn));
    prop.setValue(INDICES_KEYS[13], "" + rightTableView.getColumns().indexOf(hitTypeColumn));
    prop.setValue(INDICES_KEYS[14], "" + rightTableView.getColumns().indexOf(animationIdColumn));
    prop.setValue(INDICES_KEYS[15], "" + rightTableView.getColumns().indexOf(message1Column));
    prop.setValue(INDICES_KEYS[16], "" + rightTableView.getColumns().indexOf(message2Column));
    prop.setValue(INDICES_KEYS[17],
        "" + rightTableView.getColumns().indexOf(requiredWtypeId1Column));
    prop.setValue(INDICES_KEYS[18],
        "" + rightTableView.getColumns().indexOf(requiredWtypeId2Column));
    prop.setValue(INDICES_KEYS[19], "" + rightTableView.getColumns().indexOf(damageTypeColumn));
    prop.setValue(INDICES_KEYS[20], "" + rightTableView.getColumns().indexOf(damageElementColumn));
    prop.setValue(INDICES_KEYS[21], "" + rightTableView.getColumns().indexOf(formulaColumn));
    prop.setValue(INDICES_KEYS[22], "" + rightTableView.getColumns().indexOf(varianceColumn));
    prop.setValue(INDICES_KEYS[23], "" + rightTableView.getColumns().indexOf(criticalColumn));
    prop.setValue(INDICES_KEYS[24], "" + rightTableView.getColumns().indexOf(effectsColumn));
    prop.setValue(INDICES_KEYS[25], "" + rightTableView.getColumns().indexOf(noteColumn));
    prop.write();
  }

  /**
   * 前のセルに選択を移す.
   */
  public void movePrevious() {
    if (leftManager.isSelected()) {
      leftManager.movePrevious();
    } else if (rightManager.isSelected()) {
      rightManager.movePrevious();
    }
  }

  /**
   * 次のセルに選択を移す.
   */
  public void moveNext() {
    if (leftManager.isSelected()) {
      leftManager.moveNext();
    } else if (rightManager.isSelected()) {
      rightManager.moveNext();
    }
  }

  /**
   * 選択行の使用効果タブのセルのテキストを読み取り、
   * 使用効果プレビューを更新する。
   */
  public void updateEffectsPane() {
    if (!rightTableView.getSelectionModel().isEmpty()
        && rightTableView.getSelectionModel().getSelectedItem() != null) {
      String effectsText = rightTableView.getSelectionModel().getSelectedItem().effectsProperty()
          .get();
      int size = rightTableView.getItems().size();
      ArrayList<String> skillsList = new ArrayList<>(size);
      skillsList.add(null);
      rightTableView.getItems()
          .forEach(item -> {
            skillsList.add(item.nameProperty().get());
          });
      mainController.updateEffectsTableView(effectsText, skillsList);
    }
  }

  public void updateEffectsCell(int selectedIndex, double[] values) {
    String effectsText = rightTableView.getSelectionModel().getSelectedItem().effectsProperty()
        .get();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root;
    try {
      root = mapper.readTree(effectsText);
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

      int rowIndex = rightTableView.getSelectionModel().getSelectedIndex();
      int columnIndex = rightTableView.getColumns().indexOf(effectsColumn);
      ColumnStrategy strategy = new EffectsColumnStrategy(rightTableView, rowIndex, this);

      ICommand command = new TableCellUpdateCommand(rightTableView, rowIndex, columnIndex,
          result, strategy);
      mainController.invoke(command);
      mainController.pushUndoCount(1);
    } catch (IOException e) {
      new MyLogger(this.getClass().getName()).getLogger().log(Level.SEVERE, "JsonReadTreeError", e);
    }
  }

  void updateAxisLabels(int columnIndex, int rowIndex) {
    mainController.updateAxisLabels(columnIndex, rowIndex);
  }

  void updateHeader(String id, String name) {
    idLabel.setText(id);
    nameTextField.setText(name);
  }

  void updateInsertComboBox(int columnIndex) {
    if (rightManager.isSelected()) {
      insertComboBox.setDisable(false);
      if (columnIndex == rightTableView.getColumns().indexOf(scopeColumn)) {
        insertComboBox.setItems(scopeItems);
      } else if (columnIndex == rightTableView.getColumns().indexOf(occasionColumn)) {
      } else {
        insertComboBox.setDisable(true);
      }

    }
  }

  void changeDisablePreviews(boolean b) {
    mainController.changeDisablePreviews(b);
  }

  void updateNotePane() {
    if (!rightTableView.getSelectionModel().isEmpty()
        && rightTableView.getSelectionModel().getSelectedItem() != null) {
      int selectedIndex = rightTableView.getSelectionModel().getSelectedIndex();
      String note = rightTableView.getItems().get(selectedIndex).noteProperty().get();
      mainController.setNoteText(note);
    }
  }

  private String convertJsonText(double[] values) {
    ObjectMapper mapper = new ObjectMapper();
    Effect effect = new Effect(values);
    try {
      return mapper.writeValueAsString(effect);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getNormalAttackText() {
    return rightTableView.getItems().get(0).nameProperty().get();
  }

  public String getSelectedEffects() {
    return rightTableView.getSelectionModel().getSelectedItem().effectsProperty().get();
  }

  /**
   * 選択中のセル位置によってカラム戦略クラスを変更する。
   */
  private ColumnStrategy getStrategy(int rowIndex) {
    if (leftManager.isSelected()) {
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
    } else if (rightManager.isSelected()) {
      int columnIndex = rightManager.getSelectedCellColumnIndex();
      columnIndex += 3;
      if (columnIndex == rightTableView.getColumns().indexOf(descriptionColumn)) {
        return new DescriptionColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(stypeIdColumn)) {
        return new StypeIdColumnStrategy(rightTableView, rowIndex);
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
        return new AnimationIdColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(message1Column)) {
        return new Message1ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(message2Column)) {
        return new Message2ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId1Column)) {
        return new RequiredWtypeId1ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(requiredWtypeId2Column)) {
        return new RequiredWtypeId2ColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageTypeColumn)) {
        return new DamageTypeColumnStrategy(rightTableView, rowIndex);
      } else if (columnIndex == rightTableView.getColumns().indexOf(damageElementColumn)) {
        return new DamageElementColumnStrategy(rightTableView, rowIndex);
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
    }
    return null;
  }

  /**
   * ファイルからjsonデータを取り出してテーブルビューに追加する。
   * @param file
   *          jsonファイル
   */
  public void setSkillDatas(File skillsFile, File systemFile) {
    rightTableView.getItems().clear();

    ObjectMapper mapper = new ObjectMapper();
    skillTypeList = UtilJson.makeDataList(systemFile, "skillTypes", "なし");
    try {
      JsonNode skillsRoot = mapper.readTree(skillsFile);
      IntStream.range(1, skillsRoot.size())
          .forEach(index -> {
            JsonNode children = skillsRoot.get(index);
            rightTableView.getItems().add(UtilJson.makeSkillRecord(children, skillTypeList));
          });
      updateEffectsPane();
      updateNotePane();
      UtilTableView.bindScrollBar(leftTableView, rightTableView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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

  class Effect {
    public int code;
    public int dataId;
    public double value1;
    public double value2;

    public Effect(double[] values) {
      this.code = (int) values[0];
      this.dataId = (int) values[1];
      this.value1 = values[2];
      this.value2 = values[3];
    }

  }
}
