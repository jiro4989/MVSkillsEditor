package application.effects;

import java.io.IOException;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.MainController;
import application.effects.edit.EditStage;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EffectsTableViewBorderPaneController {
  private MainController mainController;

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
    effectsTableView.setOnMouseClicked(click -> {
      if (click.getClickCount() == 2) {
        openEditStage();
      }
    });
  }

  private static final String FORMAT = "%.0f";

  public void update(String effectsText) {
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

        String type = EffectsCodes.convertCodeIdToCodeText(codeId);
        String content = "";
        if (codeId == 11 || codeId == 12) {
          content = String.format(FORMAT, value1 * 100) + "%";
        } else if (codeId == 13) {
          content = String.format(FORMAT, value1);
        } else if (codeId == 21 || codeId == 22) {
        } else if (codeId == 31 || codeId == 32) {
        } else if (codeId == 33 || codeId == 34) {
        } else if (codeId == 41) {
        } else if (codeId == 42) {
        } else if (codeId == 43) {
        } else if (codeId == 44) {
        }
        effectsTableView.getItems().add(new Effects(type, content));
      });
    } catch (IOException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }
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
}
