package util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.tableview.Skill;
import javafx.collections.ObservableList;
import util.dictionary.SkillCritical;
import util.dictionary.SkillDamageType;
import util.dictionary.SkillHitType;
import util.dictionary.SkillOccasion;
import util.dictionary.SkillScope;

public class JsonSkill {
  public int id;
  public int animationId;
  public Map<String, Object> damage;
  public String description;
  public List<JsonEffects> effects;
  public int hitType;
  public int iconIndex;
  public String message1;
  public String message2;
  public int mpCost;
  public String name;
  public String note;
  public int occasion;
  public int repeats;
  public int requiredWtypeId1;
  public int requiredWtypeId2;
  public int scope;
  public int speed;
  public int stypeId;
  public int successRate;
  public int tpCost;
  public int tpGain;

  public JsonSkill(Skill skill,
      ObservableList<String> stypeList,
      ObservableList<String> animationList,
      ObservableList<String> weaponsList,
      ObservableList<String> elementsList)
      throws NumberFormatException, JsonProcessingException, IOException {
    String tmpId = skill.idProperty().get();
    this.id = Integer.parseInt(tmpId);

    this.name = skill.nameProperty().get();

    String tmpIconIndex = skill.iconIndexProperty().get();
    this.iconIndex = Integer.parseInt(tmpIconIndex);

    this.description = skill.descriptionProperty().get();

    String stypeId = skill.stypeIdProperty().get();
    this.stypeId = convertTextToIndex(stypeList, stypeId);

    String scope = skill.scopeProperty().get();
    this.scope = SkillScope.convertToIndex(scope);

    String tmpMpCost = skill.mpCostProperty().get();
    this.mpCost = Integer.parseInt(tmpMpCost);

    String tpCost = skill.tpCostProperty().get();
    this.tpCost = Integer.parseInt(tpCost);

    String occasion = skill.occasionProperty().get();
    this.occasion = SkillOccasion.convertToIndex(occasion);

    String speed = skill.speedProperty().get();
    this.speed = Integer.parseInt(speed);

    String successRate = skill.successRateProperty().get();
    this.successRate = Integer.parseInt(successRate);

    String repeats = skill.repeatsProperty().get();
    this.repeats = Integer.parseInt(repeats);

    String tpGain = skill.tpGainProperty().get();
    this.tpGain = Integer.parseInt(tpGain);

    String hitType = skill.hitTypeProperty().get();
    this.hitType = SkillHitType.convertToIndex(hitType);

    String animationId = skill.animationIdProperty().get();
    this.animationId = convertTextToIndex(animationList, animationId);
    // アニメーションIDは先頭の要素"通常攻撃"が-1インデックスから開始しているため
    this.animationId--;

    this.message1 = skill.message1Property().get();
    this.message2 = skill.message2Property().get();

    String requiredWtypeId1 = skill.requiredWtypeId1Property().get();
    this.requiredWtypeId1 = convertTextToIndex(weaponsList, requiredWtypeId1);

    String requiredWtypeId2 = skill.requiredWtypeId2Property().get();
    this.requiredWtypeId2 = convertTextToIndex(weaponsList, requiredWtypeId2);

    damage = new HashMap<>();
    String damageType = skill.damageTypeProperty().get();
    this.damage.put("type", SkillDamageType.convertToIndex(damageType));

    String damageElement = skill.damageElementProperty().get();
    // 通常攻撃が-1からはじまっているため
    this.damage.put("elementId", convertTextToIndex(elementsList, damageElement)-1);

    String formula = skill.formulaProperty().get();
    this.damage.put("formula", formula);

    String variance = skill.varianceProperty().get();
    this.damage.put("variance", Integer.parseInt(variance));

    String critical = skill.criticalProperty().get();
    boolean bool = SkillCritical.convertToBoolean(critical);
    this.damage.put("critical", bool);

    this.effects = new ArrayList<>();
    String effects = skill.effectsProperty().get();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(effects);
    int size = root.size();
    IntStream.range(0, size)
        .forEach(index -> {
          JsonNode children = root.get(index);
          int code = children.get("code").asInt();
          int dataId = children.get("dataId").asInt();
          double value1 = children.get("value1").asDouble();
          double value2 = children.get("value2").asDouble();
          this.effects.add(new JsonEffects(code, dataId, value1, value2));
        });

    this.note = skill.noteProperty().get();
  }

  /**
   * テキストをリストの中から検索し、見つかったインデックスを返す。
   * @param list
   * @param text
   * @return インデックス、見つからなかった場合-10000返る
   */
  private int convertTextToIndex(ObservableList<String> list, String text) {
    int index = 0;
    for (String s : list) {
      if (text.equals(s)) {
        return index;
      }
      index++;
    }
    return -10000;
  }
}
