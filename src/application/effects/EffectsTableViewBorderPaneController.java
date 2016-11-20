package application.effects;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.MainController;
import application.effects.edit.EditStage;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EffectsTableViewBorderPaneController {
  private MainController mainController;
  private ArrayList<String> stateList;

  @FXML
  private TableView<Effects> effectsTableView = new TableView<>();
  @FXML
  private TableColumn<Effects, String> typeColumn = new TableColumn<>("type");
  @FXML
  private TableColumn<Effects, String> contentColumn = new TableColumn<>("content");

  @FXML
  private void initialize() {
    typeColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("type"));
    contentColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("content"));

    effectsTableView.getItems().add(new Effects("", ""));
  }

  @FXML
  private void effectsTableViewOnMouseClicked(MouseEvent click) {
    if (click.getClickCount() == 2) {
      openEditStage();
    }
  }

  /**
   * EffectsTableViewを更新する。
   * @param effectsText Jsonテキスト
   */
  public void update(String effectsText, ArrayList<String> skillsList) {
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
        String content = contentFormat(codeId, dataId, value1, value2, skillsList);
        effectsTableView.getItems().add(new Effects(type, content));
      });
      effectsTableView.getItems().add(new Effects("", ""));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Jsonの数値からTableViewの表示用のテキストに整形する。
   * @param codeId CodeId
   * @param dataId DataId
   * @param value1 Value1
   * @param value2 Value2
   * @param skillsList
   * @return 整形後のテキスト
   */
  private String contentFormat(int codeId, int dataId, double value1, double value2,
      ArrayList<String> skillsList) {
    if (codeId == 11 || codeId == 12) {
      return (int) value1 * 100 + " %";
    } else if (codeId == 13) {
      return "" + (int) value1;
    } else if (codeId == 21 || codeId == 22) {
      String stateName = stateList.get(dataId);
      return stateName + " " + (int) value1 * 100 + " %";
    } else if (codeId == 31 || codeId == 32) {
      return Parameters.values()[dataId].name() + " " + (int) value1 + " ターン";
    } else if (codeId == 33 || codeId == 34) {
      return Parameters.values()[dataId].name();
    } else if (codeId == 41) {
      return "逃げる";
    } else if (codeId == 42) {
      return Parameters.values()[dataId].name() + " ＋ " + (int) value1;
    } else if (codeId == 43) {
      return skillsList.get(dataId);
    } else if (codeId == 44) {
    }
    return "";
  }

  /**
   * 使用効果の編集画面を開く
   */
  private void openEditStage() {
    EditStage editStage = new EditStage();
    editStage.showAndWait();
  }

  public void setMainController(MainController aMainController) {
    mainController = aMainController;
  }

  public void setDisable(boolean disable) {
    effectsTableView.setDisable(disable);
  }

  public void setStateDatas(File stateData) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode root = mapper.readTree(stateData);
      int size = root.size();
      stateList = new ArrayList<>(size);
      stateList.add("通常攻撃");
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
}
