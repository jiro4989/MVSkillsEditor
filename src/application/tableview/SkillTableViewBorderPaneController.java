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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jiro.lib.java.util.PropertiesHundler;
import util.MyLogger;

public class SkillTableViewBorderPaneController {
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
  /**
   * 現在のカラム戦略クラス
   */
  private ColumnStrategy currentStrategy;

  private File iconFile;
  private ObservableList<String> scopeItems = FXCollections.observableArrayList(Scope.getNameList());
  /**
   * 各カラムのインデックス配列
   */
  private int[] columnIndices = new int[INDICES_KEYS.length];

  @FXML private Label idLabel;
  @FXML private TextField nameTextField;
  @FXML private ComboBox<String> insertComboBox;

  @FXML private TableView<Skill> skillTableView = new TableView<>();
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

  @SuppressWarnings("unchecked")
  @FXML
  private void initialize() {
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

    // テーブルビューの複数範囲選択の有効化
    TableView.TableViewSelectionModel<Skill> model = skillTableView.getSelectionModel();
    model.setSelectionMode(SelectionMode.MULTIPLE);
    model.setCellSelectionEnabled(true);
    skillTableView.setFixedCellSize(50);

    // 各種テーブルカラムのカスタマイズ
    skillTableView.getColumns()
        .forEach(column -> settingTableColumn((TableColumn<Skill, String>) column));
    descriptionColumn.setCellFactory(TextAreaTableCell.forTableColumn(this));
    iconIndexColumn.setCellFactory(col -> new IconTableCell());
    scopeColumn.setCellFactory(col -> {
      return new ComboBoxTableCell<>(scopeItems);
    });

    // TEST
    // ObservableList<String> boolItems =
    // FXCollections.observableArrayList("あり", "なし");
    // criticalColumn.setCellFactory(col -> new
    // ButtonCell());

    skillTableView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          updateEffectsPane();
          updateNotePane();
          mainController.changeDisablePreviews(false);
          idLabel.setText(newValue.idProperty().get());
          nameTextField.setText(newValue.nameProperty().get());
        });

    skillTableView.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.getTableColumn() != null) {
        int columnIndex = newVal.getColumn();
        int rowIndex = skillTableView.getSelectionModel().getSelectedIndex();
        mainController.updateAxisLabels(columnIndex, rowIndex);
        insertComboBox.setItems(scopeItems);
      }
    });

    initializeColumnPosition();
  }

  private void settingTableColumn(TableColumn<Skill, String> tableColumn) {
    // テーブルビューのセルを編集可能にする
    // tableColumn.setCellFactory(column -> {
    // return new TextFieldTableCell<>(new
    // DefaultStringConverter());
    // });

    // 編集が終わった後に呼び出す処理
    tableColumn.setOnEditCommit(e -> {
      skillTableView.requestFocus();
      insertText(e.getNewValue());
    });
  }

  /**
   * プロパティファイルからカラムインデックスを読み取り、カラム位置を設定する。
   */
  private void initializeColumnPosition() {
    if (prop.exists()) {
      prop.load();
      skillTableView.getColumns().clear();

      IntStream.range(0, columnIndices.length).forEach(i -> {
        columnIndices[i] = Integer.parseInt(prop.getValue(INDICES_KEYS[i]));
      });

      for (int i = 0; i < INDICES_KEYS.length; i++) {
        if (i == columnIndices[0]) {
          skillTableView.getColumns().add(i, idColumn);
        } else if (i == columnIndices[1]) {
          skillTableView.getColumns().add(i, nameColumn);
        } else if (i == columnIndices[2]) {
          skillTableView.getColumns().add(i, iconIndexColumn);
        } else if (i == columnIndices[3]) {
          skillTableView.getColumns().add(i, descriptionColumn);
        } else if (i == columnIndices[4]) {
          skillTableView.getColumns().add(i, stypeIdColumn);
        } else if (i == columnIndices[5]) {
          skillTableView.getColumns().add(i, scopeColumn);
        } else if (i == columnIndices[6]) {
          skillTableView.getColumns().add(i, mpCostColumn);
        } else if (i == columnIndices[7]) {
          skillTableView.getColumns().add(i, tpCostColumn);
        } else if (i == columnIndices[8]) {
          skillTableView.getColumns().add(i, occasionColumn);
        } else if (i == columnIndices[9]) {
          skillTableView.getColumns().add(i, speedColumn);
        } else if (i == columnIndices[10]) {
          skillTableView.getColumns().add(i, successRateColumn);
        } else if (i == columnIndices[11]) {
          skillTableView.getColumns().add(i, repeatsColumn);
        } else if (i == columnIndices[12]) {
          skillTableView.getColumns().add(i, tpGainColumn);
        } else if (i == columnIndices[13]) {
          skillTableView.getColumns().add(i, hitTypeColumn);
        } else if (i == columnIndices[14]) {
          skillTableView.getColumns().add(i, animationIdColumn);
        } else if (i == columnIndices[15]) {
          skillTableView.getColumns().add(i, message1Column);
        } else if (i == columnIndices[16]) {
          skillTableView.getColumns().add(i, message2Column);
        } else if (i == columnIndices[17]) {
          skillTableView.getColumns().add(i, requiredWtypeId1Column);
        } else if (i == columnIndices[18]) {
          skillTableView.getColumns().add(i, requiredWtypeId2Column);
        } else if (i == columnIndices[19]) {
          skillTableView.getColumns().add(i, damageTypeColumn);
        } else if (i == columnIndices[20]) {
          skillTableView.getColumns().add(i, damageElementColumn);
        } else if (i == columnIndices[21]) {
          skillTableView.getColumns().add(i, formulaColumn);
        } else if (i == columnIndices[22]) {
          skillTableView.getColumns().add(i, varianceColumn);
        } else if (i == columnIndices[23]) {
          skillTableView.getColumns().add(i, criticalColumn);
        } else if (i == columnIndices[24]) {
          skillTableView.getColumns().add(i, effectsColumn);
        } else if (i == columnIndices[25]) {
          skillTableView.getColumns().add(i, noteColumn);
        }
      }
    }
  }

  @FXML
  private void skillTableViewOnMouseClicked(MouseEvent event) {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      if (event.getClickCount() == 2) {
        int columnIndex = skillTableView.getSelectionModel().getSelectedCells().get(0).getColumn();
        ObservableList<TableColumn<Skill, ?>> columns = skillTableView.getColumns();

        if (columnIndex == columns.indexOf(iconIndexColumn)) {
          String indexStr = skillTableView.getSelectionModel().getSelectedItem().iconIndexProperty()
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
    if (!skillTableView.getSelectionModel().isEmpty()) {
      ObservableList<Integer> rowIndices = skillTableView.getSelectionModel().getSelectedIndices();
      rowIndices.stream().forEach(rowIndex -> {
        int columnIndex = skillTableView.getColumns().indexOf(nameColumn);
        changeColumnStrategy(rowIndex);
        ICommand command = new TableCellUpdateCommand(skillTableView, rowIndex, columnIndex,
            newText, currentStrategy);
        mainController.invoke(command);
      });

      mainController.pushUndoCount(rowIndices.size());
    }
  }

  /**
   * 選択中のセル位置によってカラム戦略クラスを変更する。
   */
  private void changeColumnStrategy(int rowIndex) {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      int columnIndex = skillTableView.getFocusModel().getFocusedCell().getColumn();
      if (columnIndex == skillTableView.getColumns().indexOf(idColumn)) {
        currentStrategy = new IdColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(nameColumn)) {
        currentStrategy = new NameColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(iconIndexColumn)) {
        currentStrategy = new IconIndexColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(descriptionColumn)) {
        currentStrategy = new DescriptionColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(stypeIdColumn)) {
        currentStrategy = new StypeIdColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(scopeColumn)) {
        currentStrategy = new ScopeColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(mpCostColumn)) {
        currentStrategy = new MpCostColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(tpCostColumn)) {
        currentStrategy = new TpCostColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(occasionColumn)) {
        currentStrategy = new OccasionColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(speedColumn)) {
        currentStrategy = new SpeedColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(successRateColumn)) {
        currentStrategy = new SuccessRateColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(repeatsColumn)) {
        currentStrategy = new RepeatsColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(tpGainColumn)) {
        currentStrategy = new TpGainColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(hitTypeColumn)) {
        currentStrategy = new HitTypeColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(animationIdColumn)) {
        currentStrategy = new AnimationIdColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(message1Column)) {
        currentStrategy = new Message1ColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(message2Column)) {
        currentStrategy = new Message2ColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(requiredWtypeId1Column)) {
        currentStrategy = new RequiredWtypeId1ColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(requiredWtypeId2Column)) {
        currentStrategy = new RequiredWtypeId2ColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(damageTypeColumn)) {
        currentStrategy = new DamageTypeColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(damageElementColumn)) {
        currentStrategy = new DamageElementColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(formulaColumn)) {
        currentStrategy = new FormulaColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(varianceColumn)) {
        currentStrategy = new VarianceColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(criticalColumn)) {
        currentStrategy = new CriticalColumnStrategy(skillTableView, rowIndex);
      } else if (columnIndex == skillTableView.getColumns().indexOf(effectsColumn)) {
        currentStrategy = new EffectsColumnStrategy(skillTableView, rowIndex, this);
      } else if (columnIndex == skillTableView.getColumns().indexOf(noteColumn)) {
        currentStrategy = new NoteColumnStrategy(skillTableView, rowIndex);
      }
    }
  }

  /**
   * カラムインデックスを記述したプロパティファイルを出力する。
   */
  public void outputPropertiesFile() {
    prop.setValue(INDICES_KEYS[0], "" + skillTableView.getColumns().indexOf(idColumn));
    prop.setValue(INDICES_KEYS[1], "" + skillTableView.getColumns().indexOf(nameColumn));
    prop.setValue(INDICES_KEYS[2], "" + skillTableView.getColumns().indexOf(iconIndexColumn));
    prop.setValue(INDICES_KEYS[3], "" + skillTableView.getColumns().indexOf(descriptionColumn));
    prop.setValue(INDICES_KEYS[4], "" + skillTableView.getColumns().indexOf(stypeIdColumn));
    prop.setValue(INDICES_KEYS[5], "" + skillTableView.getColumns().indexOf(scopeColumn));
    prop.setValue(INDICES_KEYS[6], "" + skillTableView.getColumns().indexOf(mpCostColumn));
    prop.setValue(INDICES_KEYS[7], "" + skillTableView.getColumns().indexOf(tpCostColumn));
    prop.setValue(INDICES_KEYS[8], "" + skillTableView.getColumns().indexOf(occasionColumn));
    prop.setValue(INDICES_KEYS[9], "" + skillTableView.getColumns().indexOf(speedColumn));
    prop.setValue(INDICES_KEYS[10], "" + skillTableView.getColumns().indexOf(successRateColumn));
    prop.setValue(INDICES_KEYS[11], "" + skillTableView.getColumns().indexOf(repeatsColumn));
    prop.setValue(INDICES_KEYS[12], "" + skillTableView.getColumns().indexOf(tpGainColumn));
    prop.setValue(INDICES_KEYS[13], "" + skillTableView.getColumns().indexOf(hitTypeColumn));
    prop.setValue(INDICES_KEYS[14], "" + skillTableView.getColumns().indexOf(animationIdColumn));
    prop.setValue(INDICES_KEYS[15], "" + skillTableView.getColumns().indexOf(message1Column));
    prop.setValue(INDICES_KEYS[16], "" + skillTableView.getColumns().indexOf(message2Column));
    prop.setValue(INDICES_KEYS[17],
        "" + skillTableView.getColumns().indexOf(requiredWtypeId1Column));
    prop.setValue(INDICES_KEYS[18],
        "" + skillTableView.getColumns().indexOf(requiredWtypeId2Column));
    prop.setValue(INDICES_KEYS[19], "" + skillTableView.getColumns().indexOf(damageTypeColumn));
    prop.setValue(INDICES_KEYS[20], "" + skillTableView.getColumns().indexOf(damageElementColumn));
    prop.setValue(INDICES_KEYS[21], "" + skillTableView.getColumns().indexOf(formulaColumn));
    prop.setValue(INDICES_KEYS[22], "" + skillTableView.getColumns().indexOf(varianceColumn));
    prop.setValue(INDICES_KEYS[23], "" + skillTableView.getColumns().indexOf(criticalColumn));
    prop.setValue(INDICES_KEYS[24], "" + skillTableView.getColumns().indexOf(effectsColumn));
    prop.setValue(INDICES_KEYS[25], "" + skillTableView.getColumns().indexOf(noteColumn));
    prop.write();
  }

  /**
   * 前のセルに選択を移す.
   */
  public void movePrevious() {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      skillTableView.getSelectionModel().selectAboveCell();
    }
  }

  /**
   * 次のセルに選択を移す.
   */
  public void moveNext() {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      skillTableView.getSelectionModel().selectBelowCell();
    }
  }

  /**
   * 選択行の使用効果タブのセルのテキストを読み取り、
   * 使用効果プレビューを更新する。
   */
  public void updateEffectsPane() {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      String effectsText = skillTableView.getSelectionModel().getSelectedItem().effectsProperty()
          .get();
      int size = skillTableView.getItems().size();
      ArrayList<String> skillsList = new ArrayList<>(size);
      skillsList.add(null);
      skillTableView.getItems()
          .forEach(item -> {
            skillsList.add(item.nameProperty().get());
          });
      mainController.updateEffectsTableView(effectsText, skillsList);
    }
  }

  public void updateEffectsCell(int selectedIndex, double[] values) {
    String effectsText = skillTableView.getSelectionModel().getSelectedItem().effectsProperty()
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
      final String result = "[" + String.join(",", textList) + "]";

      int rowIndex = skillTableView.getSelectionModel().getSelectedIndex();
      int columnIndex = skillTableView.getColumns().indexOf(effectsColumn);
      currentStrategy = new EffectsColumnStrategy(skillTableView, rowIndex, this);

      ICommand command = new TableCellUpdateCommand(skillTableView, rowIndex, columnIndex,
          result, currentStrategy);
      mainController.invoke(command);
      mainController.pushUndoCount(1);
    } catch (IOException e) {
      new MyLogger(this.getClass().getName()).getLogger().log(Level.SEVERE, "JsonReadTreeError", e);
    }
  }

  /**
   * テーブルビューにフォーカスを移す。
   * テキストエリアでテキスト編集が終了したときに呼び出す。
   */
  void requestFocus() {
    skillTableView.requestFocus();
  }

  private void updateNotePane() {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      int selectedIndex = skillTableView.getSelectionModel().getSelectedIndex();
      String note = skillTableView.getItems().get(selectedIndex).noteProperty().get();
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
    return skillTableView.getItems().get(0).nameProperty().get();
  }

  public String getSelectedEffects() {
    return skillTableView.getSelectionModel().getSelectedItem().effectsProperty().get();
  }

  /**
   * ファイルからjsonデータを取り出してテーブルビューに追加する。
   * @param file
   *          jsonファイル
   */
  public void setSkillDatas(File file) {
    skillTableView.getItems().clear();

    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(file);

      IntStream.range(1, root.size())
          .forEach(index -> {
            JsonNode children = root.get(index);

            int id = children.get("id").asInt();
            final String ID = String.format("%1$04d", id);
            String name = children.get("name").asText();
            String iconIndex = children.get("iconIndex").asText();
            String description = children.get("description").asText();
            String stypeId = children.get("stypeId").asText();
            String scope = children.get("scope").asText();
            String mpCost = children.get("mpCost").asText();
            String tpCost = children.get("tpCost").asText();
            String occasion = children.get("occasion").asText();
            String speed = children.get("speed").asText();
            String successRate = children.get("successRate").asText();
            String repeats = children.get("repeats").asText();
            String tpGain = children.get("tpGain").asText();
            String hitType = children.get("hitType").asText();
            String animationId = children.get("animationId").asText();
            String message1 = children.get("message1").asText();
            String message2 = children.get("message2").asText();
            String req1 = children.get("requiredWtypeId1").asText();
            String req2 = children.get("requiredWtypeId2").asText();

            final String DMG = "damage";
            String type = children.get(DMG).get("type").asText();
            String elementId = children.get(DMG).get("elementId").asText();
            String formula = children.get(DMG).get("formula").asText();
            String variance = children.get(DMG).get("variance").asText();
            String critical = children.get(DMG).get("critical").asText();

            String effects = children.get("effects").toString();
            String note = children.get("note").asText();

            skillTableView.getItems().add(
                new Skill(ID, name, iconIndex, description, stypeId, scope, mpCost, tpCost,
                    occasion, speed, successRate, repeats, tpGain, hitType, animationId, message1,
                    message2, req1, req2, type, elementId, formula, variance, critical, effects,
                    note));
          });
      updateEffectsPane();
      updateNotePane();
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
    if (!skillTableView.getSelectionModel().isEmpty()) {
      int selectedIndex = skillTableView.getSelectionModel().getSelectedIndex();
      skillTableView.getItems().get(selectedIndex).noteProperty().set(text);
    }
  }

  public void setIconFile(File iconFile) {
    this.iconFile = iconFile;
    IconTableCell.setIconImageFile(iconFile);
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
