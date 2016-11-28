package util.json;

import java.util.List;
import java.util.Map;

import application.tableview.Skill;

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

	public JsonSkill(Skill skill) {
    String tmpId = skill.idProperty().get();
    int id = Integer.parseInt(tmpId);
    this.id = id;
    this.name = skill.nameProperty().get();
    String tmpIconIndex = skill.iconIndexProperty().get();
    int iconIndex = Integer.parseInt(tmpIconIndex);

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
	}

  private static final String NUMBER_REGEX = "^[-]*[0-9]+";

	public boolean successSetValue(Skill skill) {
	  String id = skill.idProperty().get();
	  if (!id.matches(NUMBER_REGEX)) {
	    return false;
	  }
	  return true;
	}
}
