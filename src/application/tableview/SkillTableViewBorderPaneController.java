package application.tableview;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.jfxmedia.events.NewFrameEvent;

import application.MainController;
import application.Skill;
import command.ICommand;
import command.TableCellUpdateCommand;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import jiro.lib.java.util.PropertiesHundler;
import jiro.lib.javafx.scene.control.TextAreaTableCell;
import strategy.NameColumnStrategy;

public class SkillTableViewBorderPaneController {
  private MainController mainController;
  private PropertiesHundler prop = new PropertiesHundler("column-indices");
  private static final String[] INDICES_KEYS = {
      "id-index",
      "name-index",
      "iconIndex-index",
      "description-index",
      "stypeId-index",
      "scope-index",
      "mpCost-index",
      "tpCost-index",
      "occasion-index",
      "speed-index",
      "successRate-index",
      "repeats-index",
      "tpGain-index",
      "hitType-index",
      "animationId-index",
      "message1-index",
      "message2-index",
      "requiredWtypeId1-index",
      "requiredWtypeId2-index",
      "damageType-index",
      "damageElementId-index",
      "formula-index",
      "variance-index",
      "critical-index",
      "effects-index",
      "note-index",
  };

  @FXML
  private TableView<Skill> skillTableView = new TableView<>();
  @FXML
  private TableColumn<Skill, Integer> idColumn = new TableColumn<>("ID");
  @FXML
  private TableColumn<Skill, String> nameColumn = new TableColumn<>("名前");
  @FXML
  private TableColumn<Skill, Integer> iconIndexColumn = new TableColumn<>("アイコン");
  @FXML
  private TableColumn<Skill, String> descriptionColumn = new TableColumn<>("説明");
  @FXML
  private TableColumn<Skill, Integer> stypeIdColumn = new TableColumn<>("スキルタイプ");
  @FXML
  private TableColumn<Skill, Integer> scopeColumn = new TableColumn<>("範囲");
  @FXML
  private TableColumn<Skill, Integer> mpCostColumn = new TableColumn<>("消費MP");
  @FXML
  private TableColumn<Skill, Integer> tpCostColumn = new TableColumn<>("消費TP");
  @FXML
  private TableColumn<Skill, Integer> occasionColumn = new TableColumn<>("使用可能時");
  @FXML
  private TableColumn<Skill, Integer> speedColumn = new TableColumn<>("速度補正");
  @FXML
  private TableColumn<Skill, Integer> successRateColumn = new TableColumn<>("成功率");
  @FXML
  private TableColumn<Skill, Integer> repeatsColumn = new TableColumn<>("連続回数");
  @FXML
  private TableColumn<Skill, Integer> tpGainColumn = new TableColumn<>("得TP");
  @FXML
  private TableColumn<Skill, Integer> hitTypeColumn = new TableColumn<>("命中タイプ");
  @FXML
  private TableColumn<Skill, Integer> animationIdColumn = new TableColumn<>("アニメーション");
  @FXML
  private TableColumn<Skill, String> message1Column = new TableColumn<>("メッセージ1");
  @FXML
  private TableColumn<Skill, String> message2Column = new TableColumn<>("メッセージ2");
  @FXML
  private TableColumn<Skill, Integer> requiredWtypeId1Column = new TableColumn<>("必要武器1");
  @FXML
  private TableColumn<Skill, Integer> requiredWtypeId2Column = new TableColumn<>("必要武器2");
  @FXML
  private TableColumn<Skill, Integer> damageTypeColumn = new TableColumn<>("ダメージタイプ");
  @FXML
  private TableColumn<Skill, Integer> damageElementColumn = new TableColumn<>("属性");
  @FXML
  private TableColumn<Skill, String> formulaColumn = new TableColumn<>("計算式");
  @FXML
  private TableColumn<Skill, Integer> varianceColumn = new TableColumn<>("分散度");
  @FXML
  private TableColumn<Skill, Integer> criticalColumn = new TableColumn<>("会心率");
  @FXML
  private TableColumn<Skill, String> effectsColumn = new TableColumn<>("使用効果");
  @FXML
  private TableColumn<Skill, String> noteColumn = new TableColumn<>("メモ");

  @FXML
  private void initialize() {
    idColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("name"));
    iconIndexColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("iconIndex"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("description"));
    stypeIdColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("stypeId"));
    scopeColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("scope"));
    mpCostColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("mpCost"));
    tpCostColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("tpCost"));
    occasionColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("occasion"));
    speedColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("speed"));
    successRateColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("successRate"));
    repeatsColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("repeats"));
    tpGainColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("tpGain"));
    hitTypeColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("hitType"));
    animationIdColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("animationId"));
    message1Column.setCellValueFactory(new PropertyValueFactory<Skill, String>("message1"));
    message2Column.setCellValueFactory(new PropertyValueFactory<Skill, String>("message2"));
    requiredWtypeId1Column
        .setCellValueFactory(new PropertyValueFactory<Skill, Integer>("requiredWtypeId1"));
    requiredWtypeId2Column
        .setCellValueFactory(new PropertyValueFactory<Skill, Integer>("requiredWtypeId2"));
    damageTypeColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("damageType"));
    damageElementColumn
        .setCellValueFactory(new PropertyValueFactory<Skill, Integer>("damageElement"));
    formulaColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("formula"));
    varianceColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("variance"));
    criticalColumn.setCellValueFactory(new PropertyValueFactory<Skill, Integer>("critical"));
    effectsColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("effects"));
    noteColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("note"));

    // テーブルビューの複数範囲選択の有効化
    TableView.TableViewSelectionModel<Skill> model = skillTableView.getSelectionModel();
    model.setSelectionMode(SelectionMode.MULTIPLE);
    model.setCellSelectionEnabled(true);
    skillTableView.setFixedCellSize(50);

    // テーブルビューのセルを編集可能にする
    nameColumn.setCellFactory(column -> {
      return new TextFieldTableCell<>(new DefaultStringConverter());
    });
    descriptionColumn.setCellFactory(TextAreaTableCell.forTableColumn());

    // 編集が終わった後に呼び出す処理
    nameColumn.setOnEditCommit(e -> {
      skillTableView.requestFocus();
      insertText(e.getNewValue());
    });

    skillTableView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          updateEffectsPane();
          updateNotePane();
        });

    skillTableView.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal.getTableColumn() != null) {
        int x = newVal.getColumn();
        int y = skillTableView.getSelectionModel().getSelectedIndex();
        mainController.updateAxisLabels(x, y);
      }
    });

    if (prop.exists()) {
      prop.load();
//      skillTableView.getColumns().clear();
//      String idIndex = prop.getValue("id-index");
//      skillTableView.getColumns().remove(0);
//      skillTableView.getColumns().set(Integer.valueOf(idIndex), idColumn);
//      removeIntegerColumn(INDICES_KEYS[0], idColumn);
//      removeStringColumn(INDICES_KEYS[1], nameColumn);
//      removeIntegerColumn(INDICES_KEYS[2], iconIndexColumn);
//      removeStringColumn(INDICES_KEYS[3], descriptionColumn);
//      removeIntegerColumn(INDICES_KEYS[4], stypeIdColumn);
//      removeIntegerColumn(INDICES_KEYS[5], scopeColumn);
//      removeIntegerColumn(INDICES_KEYS[6], mpCostColumn);
//      removeIntegerColumn(INDICES_KEYS[7], tpCostColumn);
//      removeIntegerColumn(INDICES_KEYS[8], occasionColumn);
//      removeIntegerColumn(INDICES_KEYS[9], speedColumn);
//      removeIntegerColumn(INDICES_KEYS[10], successRateColumn);
//      removeIntegerColumn(INDICES_KEYS[11], repeatsColumn);
//      removeIntegerColumn(INDICES_KEYS[12], tpGainColumn);
//      removeIntegerColumn(INDICES_KEYS[13], hitTypeColumn);
//      removeIntegerColumn(INDICES_KEYS[14], animationIdColumn);
//      removeStringColumn(INDICES_KEYS[15], message1Column);
//      removeStringColumn(INDICES_KEYS[16], message2Column);
//      removeIntegerColumn(INDICES_KEYS[17], requiredWtypeId1Column);
//      removeIntegerColumn(INDICES_KEYS[18], requiredWtypeId2Column);
//      removeIntegerColumn(INDICES_KEYS[19], damageTypeColumn);
//      removeIntegerColumn(INDICES_KEYS[20], damageElementColumn);
//      removeStringColumn(INDICES_KEYS[21], formulaColumn);
//      removeIntegerColumn(INDICES_KEYS[22], varianceColumn);
//      removeIntegerColumn(INDICES_KEYS[23], criticalColumn);
//      removeStringColumn(INDICES_KEYS[24], effectsColumn);
//      removeStringColumn(INDICES_KEYS[25], noteColumn);
    }
  }

  private void removeIntegerColumn(String propKey, TableColumn<Skill, Integer> column) {
      int index = skillTableView.getColumns().indexOf(column);
      skillTableView.getColumns().remove(index);

      String columnIndex = prop.getValue(propKey);
      skillTableView.getColumns().set(Integer.valueOf(columnIndex), column);
  }

  private void removeStringColumn(String propKey, TableColumn<Skill, String> column) {
      int index = skillTableView.getColumns().indexOf(column);
      skillTableView.getColumns().remove(index);

      String columnIndex = prop.getValue(propKey);
      skillTableView.getColumns().set(Integer.valueOf(columnIndex), column);
  }

  /**
   * ファイルからjsonデータを取り出してテーブルビューに追加する。
   *
   * @param file
   *          jsonファイル
   */
  public void setDatas(File file) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(file);

      IntStream.range(0, root.size()).forEach(index -> {
        JsonNode children = root.get(index);
        skillTableView.getItems().add(new Skill(index, "sample", 0, "desc", 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, "msg1", "msg2", 1, 2, 1, 2, "formula", 20, 20, "effects", "note"));

        JsonNode value = children.get("id");
        if (value != null) {
          skillTableView.getItems().get(index).setId(value.asInt());
        }
        value = children.get("name");
        if (value != null) {
          skillTableView.getItems().get(index).setName(value.asText());
        }
        value = children.get("iconIndex");
        if (value != null) {
          skillTableView.getItems().get(index).setIconIndex(value.asInt());
        }
        value = children.get("description");
        if (value != null) {
          skillTableView.getItems().get(index).setDescription(value.asText());
        }
        value = children.get("stypeId");
        if (value != null) {
          skillTableView.getItems().get(index).setStypeId(value.asInt());
        }
        value = children.get("scope");
        if (value != null) {
          skillTableView.getItems().get(index).setScope(value.asInt());
        }
        value = children.get("mpCost");
        if (value != null) {
          skillTableView.getItems().get(index).setMpCost(value.asInt());
        }
        value = children.get("tpCost");
        if (value != null) {
          skillTableView.getItems().get(index).setTpCost(value.asInt());
        }
        value = children.get("occasion");
        if (value != null) {
          skillTableView.getItems().get(index).setOccasion(value.asInt());
        }
        value = children.get("speed");
        if (value != null) {
          skillTableView.getItems().get(index).setSpeed(value.asInt());
        }
        value = children.get("successRate");
        if (value != null) {
          skillTableView.getItems().get(index).setSuccessRate(value.asInt());
        }
        value = children.get("repeats");
        if (value != null) {
          skillTableView.getItems().get(index).setRepeats(value.asInt());
        }
        value = children.get("tpGain");
        if (value != null) {
          skillTableView.getItems().get(index).setTpGain(value.asInt());
        }
        value = children.get("hitType");
        if (value != null) {
          skillTableView.getItems().get(index).setHitType(value.asInt());
        }
        value = children.get("animationId");
        if (value != null) {
          skillTableView.getItems().get(index).setAnimationId(value.asInt());
        }
        value = children.get("message1");
        if (value != null) {
          skillTableView.getItems().get(index).setMessage1(value.asText());
        }
        value = children.get("message2");
        if (value != null) {
          skillTableView.getItems().get(index).setMessage2(value.asText());
        }
        value = children.get("requiredWtypeId1");
        if (value != null) {
          skillTableView.getItems().get(index).setRequiredWtypeId1(value.asInt());
        }
        value = children.get("requiredWtypeId2");
        if (value != null) {
          skillTableView.getItems().get(index).setRequiredWtypeId2(value.asInt());
        }
        value = children.get("damage");
        if (value != null) {
          // あとで
        }
        value = children.get("effects");
        if (value != null) {
          skillTableView.getItems().get(index).setEffects(value.asText());
        }
        value = children.get("note");
        if (value != null) {
          skillTableView.getItems().get(index).setNote(value.asText());
        }
      });
      skillTableView.getItems().remove(0);
      updateEffectsPane();
      updateNotePane();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 選択中のセルにテキストを挿入する。
   *
   * @param newText
   *          新しく挿入するテキスト
   */
  public void insertText(String newText) {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      ObservableList<Integer> rowIndices = skillTableView.getSelectionModel().getSelectedIndices();
      rowIndices.stream().forEach(rowIndex -> {
        int columnIndex = skillTableView.getColumns().indexOf(nameColumn);
        ICommand command = new TableCellUpdateCommand(skillTableView, rowIndex, columnIndex,
            newText, new NameColumnStrategy(skillTableView, rowIndex));
        mainController.invoke(command);
      });

      mainController.undoCountPush(rowIndices.size());
    }
  }

  /**
   * カラムインデックスを記述したプロパティファイルを出力する。
   */
  public void outputPropertiesFile() {
    prop.setValue(INDICES_KEYS[0], ""+skillTableView.getColumns().indexOf(idColumn));
    prop.setValue(INDICES_KEYS[1], ""+skillTableView.getColumns().indexOf(nameColumn));
    prop.setValue(INDICES_KEYS[2], ""+skillTableView.getColumns().indexOf(iconIndexColumn));
    prop.setValue(INDICES_KEYS[3], ""+skillTableView.getColumns().indexOf(descriptionColumn));
    prop.setValue(INDICES_KEYS[4], ""+skillTableView.getColumns().indexOf(stypeIdColumn));
    prop.setValue(INDICES_KEYS[5], ""+skillTableView.getColumns().indexOf(scopeColumn));
    prop.setValue(INDICES_KEYS[6], ""+skillTableView.getColumns().indexOf(mpCostColumn));
    prop.setValue(INDICES_KEYS[7], ""+skillTableView.getColumns().indexOf(tpCostColumn));
    prop.setValue(INDICES_KEYS[8], ""+skillTableView.getColumns().indexOf(occasionColumn));
    prop.setValue(INDICES_KEYS[9], ""+skillTableView.getColumns().indexOf(speedColumn));
    prop.setValue(INDICES_KEYS[10], ""+skillTableView.getColumns().indexOf(successRateColumn));
    prop.setValue(INDICES_KEYS[11], ""+skillTableView.getColumns().indexOf(repeatsColumn));
    prop.setValue(INDICES_KEYS[12], ""+skillTableView.getColumns().indexOf(tpGainColumn));
    prop.setValue(INDICES_KEYS[13], ""+skillTableView.getColumns().indexOf(hitTypeColumn));
    prop.setValue(INDICES_KEYS[14], ""+skillTableView.getColumns().indexOf(animationIdColumn));
    prop.setValue(INDICES_KEYS[15], ""+skillTableView.getColumns().indexOf(message1Column));
    prop.setValue(INDICES_KEYS[16], ""+skillTableView.getColumns().indexOf(message2Column));
    prop.setValue(INDICES_KEYS[17], ""+skillTableView.getColumns().indexOf(requiredWtypeId1Column));
    prop.setValue(INDICES_KEYS[18], ""+skillTableView.getColumns().indexOf(requiredWtypeId2Column));
    prop.setValue(INDICES_KEYS[19], ""+skillTableView.getColumns().indexOf(damageTypeColumn));
    prop.setValue(INDICES_KEYS[20], ""+skillTableView.getColumns().indexOf(damageElementColumn));
    prop.setValue(INDICES_KEYS[21], ""+skillTableView.getColumns().indexOf(formulaColumn));
    prop.setValue(INDICES_KEYS[22], ""+skillTableView.getColumns().indexOf(varianceColumn));
    prop.setValue(INDICES_KEYS[23], ""+skillTableView.getColumns().indexOf(criticalColumn));
    prop.setValue(INDICES_KEYS[24], ""+skillTableView.getColumns().indexOf(effectsColumn));
    prop.setValue(INDICES_KEYS[25], ""+skillTableView.getColumns().indexOf(noteColumn));
    prop.write();
  }

  private void updateEffectsPane() {
  }

  private void updateNotePane() {
    if (!skillTableView.getSelectionModel().isEmpty()) {
      int selectedIndex = skillTableView.getSelectionModel().getSelectedIndex();
      String note = skillTableView.getItems().get(selectedIndex).noteProperty().get();
      mainController.setNoteText(note);
    }
  }

  public void setMainController(MainController aMainController) {
    mainController = aMainController;
  }
}
