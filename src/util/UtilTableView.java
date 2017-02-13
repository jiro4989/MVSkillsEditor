package util;

import java.util.Set;

import application.tableview.Skill;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class UtilTableView {

  public static void bindScrollBar(TableView<Skill> leftTable, TableView<Skill> rightTable) {
    ScrollBar leftVerticalScrollBar = getScrollBar(leftTable, true);
    ScrollBar rightVerticalScrollBar = getScrollBar(rightTable, true);
    leftVerticalScrollBar.valueProperty().bindBidirectional(rightVerticalScrollBar.valueProperty());
  }

  private static ScrollBar getScrollBar(TableView<Skill> tableView, boolean vertical) {
    Set<Node> nodes = tableView.lookupAll(".scroll-bar");
    for (Node node : nodes) {
      if (node instanceof ScrollBar) {
        ScrollBar scrollBar = (ScrollBar) node;
        if (vertical && scrollBar.getOrientation() == Orientation.VERTICAL) {
          return scrollBar;
        } else if (!vertical && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
          return scrollBar;
        }
      }
    }
    throw new IllegalStateException("Not found!");
  }

  /**
   * 行番号の位置のデータをSkillインスタンスとして取得する。
   * @param tableView
   * @param rowIndex
   * @return
   */
  public static Skill getSkillRecord(TableView<Skill> tableView, int rowIndex) {
    Skill skill = tableView.getItems().get(rowIndex);
    String id = skill.idProperty().get();
    String name = skill.nameProperty().get();
    String iconIndex = skill.iconIndexProperty().get();
    String description = skill.descriptionProperty().get();
    String stypeId = skill.stypeIdProperty().get();
    String scope = skill.scopeProperty().get();
    String mpCost = skill.mpCostProperty().get();
    String tpCost = skill.tpCostProperty().get();
    String occasion = skill.occasionProperty().get();
    String speed = skill.speedProperty().get();
    String successRate = skill.successRateProperty().get();
    String repeats = skill.repeatsProperty().get();
    String tpGain = skill.tpGainProperty().get();
    String hitType = skill.hitTypeProperty().get();
    String animationId = skill.animationIdProperty().get();
    String message1 = skill.message1Property().get();
    String message2 = skill.message2Property().get();
    String requiredWtypeId1 = skill.requiredWtypeId1Property().get();
    String requiredWtypeId2 = skill.requiredWtypeId2Property().get();
    String damageType = skill.damageTypeProperty().get();
    String damageElement = skill.damageElementProperty().get();
    String formula = skill.formulaProperty().get();
    String variance = skill.varianceProperty().get();
    String critical = skill.criticalProperty().get();
    String effects = skill.effectsProperty().get();
    String note = skill.noteProperty().get();
    return new Skill(id, name, iconIndex, description, stypeId, scope, mpCost, tpCost, occasion,
        speed, successRate, repeats, tpGain, hitType, animationId, message1, message2,
        requiredWtypeId1, requiredWtypeId2, damageType, damageElement, formula, variance, critical,
        effects, note);
  }
}
