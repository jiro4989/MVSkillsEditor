package application.tableview.strategy.cell;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
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
      mapper.readTree(text);
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
