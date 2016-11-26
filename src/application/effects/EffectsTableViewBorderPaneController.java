package application.effects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.MainController;
import application.effects.edit.EditStage;
import application.effects.edit.strategy.EditStrategyManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.UtilDouble;

public class EffectsTableViewBorderPaneController {
  private MainController mainController;
  private List<String> stateList;
  private List<String> commonEventList;
  private List<String> skillList;
  private int currentSelectedIndex = 0;

  @FXML private TableView<Effects> effectsTableView = new TableView<>();
  @FXML private TableColumn<Effects, String> typeColumn = new TableColumn<>("type");
  @FXML private TableColumn<Effects, String> contentColumn = new TableColumn<>("content");

  @FXML private ContextMenu contextMenu;
  @FXML private MenuItem editMenuItem;
  @FXML private MenuItem cutMenuItem;
  @FXML private MenuItem copyMenuItem;
  @FXML private MenuItem pasteMenuItem;
  @FXML private MenuItem deleteMenuItem;
  @FXML private MenuItem selectAllMenuItem;

  @FXML
  private void initialize() {
    typeColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("type"));
    contentColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("content"));

    effectsTableView.getItems().add(new Effects("", ""));

    effectsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  @FXML
  private void effectsTableViewOnKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      editMenuItemOnAction();
    }
  }

  @FXML
  private void effectsTableViewOnMouseClicked(MouseEvent click) {
    if (!effectsTableView.getSelectionModel().isEmpty()
        && click.getClickCount() == 2) {
      openEditStage(getSelectedValues());
    }
  }

  /**
   * 選択しているレコードのデータを取得する。
   * @return レコード
   */
  private double[] getSelectedValues() {
    int selectedIndex = effectsTableView.getSelectionModel().getSelectedIndex();
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(mainController.getSelectedEffects());
      int rootSize = root.size();
      if (selectedIndex < rootSize) {
        return getValues(root, selectedIndex);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new double[] {
        -1, -1, -1, -1
    };
  }

  private double[] getValues(JsonNode root, int index) {
    JsonNode node = root.get(index);
    double[] values = new double[4];
    values[0] = node.get("code").asInt();
    values[1] = node.get("dataId").asInt();
    values[2] = node.get("value1").asDouble();
    values[3] = node.get("value2").asDouble();
    return values;
  }

  private List<Double[]> copyValues;

  @FXML
  private void contextMenuOnShown() {
    pasteMenuItem.setDisable(copyValues == null);
  }

  @FXML
  private void editMenuItemOnAction() {
    if (!effectsTableView.getSelectionModel().isEmpty()) {
      openEditStage(getSelectedValues());
    }
  }

  @FXML
  private void cutMenuItemOnAction() {
    // ==================================================
    // 未実装
    // ==================================================
  }

  @FXML
  private void deleteMenuItemOnAction() {
    if (!effectsTableView.getSelectionModel().isEmpty()) {
      ObservableList<Integer> indicies = effectsTableView.getSelectionModel().getSelectedIndices();
      int size = indicies.size();
      String text = mainController.getSelectedEffects();
      Pattern p = Pattern.compile("[^\\[].*[^\\]]");
      Matcher m = p.matcher(text);
      if (m.find()) {
        text = m.group();
      }

      String[] array = text.split("(?<=\\}),");
      List<String> list = new LinkedList<>(Arrays.asList(array));
      int count = 0;
      for (int index: indicies) {
        index -= count;
        list.remove(index);
        count++;
      }
      String newText = "[" + String.join(",", list) + "]";
      mainController.updateEffectsCell(newText);
    }
  }

  @FXML
  private void selectAllMenuItemOnAction() {
    // ==================================================
    // 未実装
    // ==================================================
  }

  @FXML
  private void copyMenuItemOnAction() {
    if (!effectsTableView.getSelectionModel().isEmpty()) {
      ObservableList<Integer> indicies = effectsTableView.getSelectionModel().getSelectedIndices();
      copyValues = new ArrayList<>(indicies.size());
      for (int index : indicies) {
        ObjectMapper mapper = new ObjectMapper();
        try {
          JsonNode root = mapper.readTree(mainController.getSelectedEffects());
          if (index < effectsTableView.getItems().size() - 1) {
            double[] doubles = getValues(root, index);
            copyValues.add(UtilDouble.convertDoublePrimitiveToWrapper(doubles));
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @FXML
  private void pasteMenuItemOnAction() {
    if (!effectsTableView.getSelectionModel().isEmpty() && copyValues != null) {
      int size = copyValues.size();
      currentSelectedIndex = effectsTableView.getSelectionModel().getSelectedIndex();

      IntStream.range(0, size).forEach(i -> {
        Double[] values = copyValues.get(i);
        mainController.updateEffectsCellNonPushUndo(currentSelectedIndex,
            UtilDouble.convertDoubleWrapperToPrimitive(values));
        currentSelectedIndex++;
      });
      mainController.pushUndoCount(size);
    }
  }

  /**
   * EffectsTableViewを更新する。
   * @param effectsText Jsonテキスト
   */
  public void update(String effectsText, List<String> aSkillList) {
    skillList = aSkillList;
    effectsTableView.getItems().clear();
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(effectsText);
      int size = root.size();
      IntStream.range(0, size).forEach(i -> {
        JsonNode node = root.get(i);
        int codeId = node.get("code").asInt();
        int dataId = node.get("dataId").asInt();
        double value1 = node.get("value1").asDouble();
        double value2 = node.get("value2").asDouble();

        String type = EffectsTypeName.convertCodeIdToCodeText(codeId);

        EditStrategyManager manager = new EditStrategyManager();
        int strategyIndex = manager.calculateStrategyIndex(codeId);
        manager.changeStrategy(strategyIndex, skillList, stateList, commonEventList);
        String content = manager.formatToContentText(codeId, dataId, value1, value2);

        effectsTableView.getItems().add(new Effects(type, content));
      });
      effectsTableView.getItems().add(new Effects("", ""));

      int tableSize = effectsTableView.getItems().size();
      currentSelectedIndex = tableSize < currentSelectedIndex ? tableSize : currentSelectedIndex;
      effectsTableView.getSelectionModel().select(currentSelectedIndex);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 使用効果の編集画面を開く
   * @param value2
   * @param value1
   * @param dataId
   * @param codeId
   */
  private void openEditStage(double[] values) {
    EditStage editStage = new EditStage(this, (int) values[0], (int) values[1], values[2],
        values[3], skillList, stateList,
        commonEventList);
    editStage.showAndWait();
  }

  public void setMainController(MainController aMainController) {
    mainController = aMainController;
  }

  public void setDisable(boolean disable) {
    effectsTableView.setDisable(disable);
  }

  public void setStateList(File stateFile, String normalAttack) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode root = mapper.readTree(stateFile);
      int size = root.size();
      stateList = new ArrayList<>(size);
      stateList.add(normalAttack);
      IntStream.range(1, size)
          .forEach(i -> {
            JsonNode children = root.get(i);
            String name = children.get("name").asText();
            stateList.add(name);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setCommonEventList(File commonEventFile) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode root = mapper.readTree(commonEventFile);
      int size = root.size();
      commonEventList = new ArrayList<>(size);
      commonEventList.add(null);
      IntStream.range(1, size)
          .forEach(i -> {
            JsonNode children = root.get(i);
            String name = children.get("name").asText();
            commonEventList.add(name);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * SkillTableViewの選択中の使用効果セルのテキストを更新する。
   * @param values
   */
  public void updateEffects(double[] values) {
    if (!effectsTableView.getSelectionModel().isEmpty()) {
      currentSelectedIndex = effectsTableView.getSelectionModel().getSelectedIndex();
      mainController.updateEffectsCell(currentSelectedIndex, values);
    }
  }

  public void addEffects(double[] values) {
    currentSelectedIndex = effectsTableView.getItems().size() - 1;
    mainController.updateEffectsCell(currentSelectedIndex, values);
  }
}
