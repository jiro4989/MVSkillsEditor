package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.tableview.Skill;
import util.dictionary.SkillCritical;
import util.dictionary.SkillDamageType;
import util.dictionary.SkillHitType;
import util.dictionary.SkillMessage;
import util.dictionary.SkillOccassion;
import util.dictionary.SkillScope;

public class UtilJson {
  public static List<String> makeDataList(File jsonFile, String key, String firstText) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(jsonFile);
      JsonNode skillTypeChild = root.get(key);
      int skillTypeSize = skillTypeChild.size();

      List<String> list = new ArrayList<>(skillTypeSize);
      list.add(firstText);

      IntStream.range(1, skillTypeSize)
          .forEach(i -> {
            list.add(skillTypeChild.get(i).asText());
          });
      return list;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Skill makeSkillRecord(JsonNode node, List<String> skillTypeList) {
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
    String occasion = SkillOccassion.convertToText(tmpOccassion);

    String speed = node.get("speed").asText();
    String successRate = node.get("successRate").asText();
    String repeats = node.get("repeats").asText();
    String tpGain = node.get("tpGain").asText();

    int tmpHitType = node.get("hitType").asInt();
    String hitType = SkillHitType.convertToText(tmpHitType);

    String animationId = node.get("animationId").asText();

    int tmpMsg1 = node.get("message1").asInt();
    String message1 = SkillMessage.convertToText(tmpMsg1);

    int tmpMsg2 = node.get("message2").asInt();
    String message2 = SkillMessage.convertToText(tmpMsg2);

    String req1 = node.get("requiredWtypeId1").asText();
    String req2 = node.get("requiredWtypeId2").asText();

    final String DMG = "damage";
    int tmpType = node.get(DMG).get("type").asInt();
    String type = SkillDamageType.convertToText(tmpType);

    String elementId = node.get(DMG).get("elementId").asText();
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
}
