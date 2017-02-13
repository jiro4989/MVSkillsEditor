package application.tableview.strategy.cell;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.tableview.Skill;
import application.tableview.SkillTableViewBorderPaneController;
import javafx.scene.control.TableView;

public class EffectsColumnStrategy extends ColumnStrategy {
  private SkillTableViewBorderPaneController controller;

  public EffectsColumnStrategy(TableView<Skill> tableView, int rowIndex,
      SkillTableViewBorderPaneController aController) {
    super();
    this.tableView = tableView;
    this.rowIndex = rowIndex;
    this.controller = aController;
  }

  @Override
  public Object getValue() {
    return tableView.getItems().get(rowIndex).effectsProperty().get();
  }

  @Override
  public void setValue(Object value) {
    tableView.getItems().get(rowIndex).effectsProperty().set((String) value);
    controller.updateEffectsPane();
  }

  @Override
  public boolean isInvokable(Object value) {
    String text = (String) value;
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(text);
      int size = root.size();
      if (size == 0) {
        return true;
      }
      for (int i = 0; i < size; i++) {
        JsonNode children = root.get(i);
        JsonNode code = children.get("code");
        JsonNode dataId = children.get("dataId");
        JsonNode value1 = children.get("value1");
        JsonNode value2 = children.get("value2");
        if (code == null
            || dataId == null
            || value1 == null
            || value2 == null) {
          return false;
        }
      }
      return true;
    } catch (JsonProcessingException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public String defaultValue(String value) {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }
}
