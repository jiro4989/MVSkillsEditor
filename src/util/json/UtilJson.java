package util.json;

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

import application.tableview.Skill;
import util.dictionary.SkillCritical;
import util.dictionary.SkillDamageType;
import util.dictionary.SkillHitType;
import util.dictionary.SkillOccasion;
import util.dictionary.SkillScope;

public class UtilJson {
  public static List<String> makeDataList(File jsonFile, String key, String firstText) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(jsonFile);
      JsonNode child = root.get(key);
      int size = child.size();

      List<String> list = new ArrayList<>(size);
      list.add(firstText);

      IntStream.range(1, size)
          .forEach(i -> {
            list.add(child.get(i).asText());
          });
      return list;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<String> makeAnimationList(File animationFile) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(animationFile);
      int size = root.size();

      List<String> list = new ArrayList<>(size + 1);
      list.add("通常攻撃");
      list.add("なし");
      IntStream.range(1, size)
          .forEach(index -> {
            JsonNode children = root.get(index);
            String name = children.get("name").asText();
            list.add(name);
          });
      return list;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Skill makeSkillRecord(JsonNode node, List<String> skillTypeList,
      List<String> animationList, List<String> weaponList, List<String> elementList) {
    int tmpId = node.get("id").asInt();
    String id = String.format("%1$04d", tmpId);
    String name = node.get("name").asText();
    String iconIndex = node.get("iconIndex").asText();
    String description = node.get("description").asText();

    int tmpStypeId = node.get("stypeId").asInt();
    String stypeId = skillTypeList.get(tmpStypeId);

    int tmpScope = node.get("scope").asInt();
    String scope = SkillScope.convertToText(tmpScope);

    String mpCost = node.get("mpCost").asText();
    String tpCost = node.get("tpCost").asText();

    int tmpOccassion = node.get("occasion").asInt();
    String occasion = SkillOccasion.convertToText(tmpOccassion);

    String speed = node.get("speed").asText();
    String successRate = node.get("successRate").asText();
    String repeats = node.get("repeats").asText();
    String tpGain = node.get("tpGain").asText();

    int tmpHitType = node.get("hitType").asInt();
    String hitType = SkillHitType.convertToText(tmpHitType);

    int tmpAnimationId = node.get("animationId").asInt();
    String animationId = animationList.get(++tmpAnimationId);

    String message1 = node.get("message1").asText();
    String message2 = node.get("message2").asText();

    int tmpReq1 = node.get("requiredWtypeId1").asInt();
    String req1 = weaponList.get(tmpReq1);
    int tmpReq2 = node.get("requiredWtypeId2").asInt();
    String req2 = weaponList.get(tmpReq2);

    final String DMG = "damage";
    int tmpType = node.get(DMG).get("type").asInt();
    String type = SkillDamageType.convertToText(tmpType);

    int tmpElementId = node.get(DMG).get("elementId").asInt();
    String elementId = elementList.get(++tmpElementId);

    String formula = node.get(DMG).get("formula").asText();
    String variance = node.get(DMG).get("variance").asText();

    boolean tmpCritical = node.get(DMG).get("critical").asBoolean();
    String critical = SkillCritical.convertToText(tmpCritical);

    String effects = node.get("effects").toString();
    String note = node.get("note").asText();

    return new Skill(id, name, iconIndex, description, stypeId, scope, mpCost, tpCost,
        occasion, speed, successRate, repeats, tpGain, hitType, animationId, message1,
        message2, req1, req2, type, elementId, formula, variance, critical, effects,
        note);

  }

  private static final Pattern p = Pattern.compile("[^\\[].*[^\\]]");

  /**
   * 使用効果のレコードの文字列リストを返す。
   * @param text
   * @return
   */
  public static List<String> getEffectsRecordList(String text) {
    Matcher m = p.matcher(text);
    if (m.find()) {
      text = m.group();
    }

    String[] array = text.split("(?<=\\}),");
    List<String> list = new LinkedList<>(Arrays.asList(array));
    return list;
  }
}
