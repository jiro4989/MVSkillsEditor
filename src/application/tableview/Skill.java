package application.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Skill {
  private StringProperty id;
  private StringProperty name;
  private StringProperty iconIndex;
  private StringProperty description;
  private StringProperty stypeId;
  private StringProperty scope;
  private StringProperty mpCost;
  private StringProperty tpCost;
  private StringProperty occasion;
  private StringProperty speed;
  private StringProperty successRate;
  private StringProperty repeats;
  private StringProperty tpGain;
  private StringProperty hitType;
  private StringProperty animationId;
  private StringProperty message1;
  private StringProperty message2;
  private StringProperty requiredWtypeId1;
  private StringProperty requiredWtypeId2;
  private StringProperty damageType;
  private StringProperty damageElement;
  private StringProperty formula;
  private StringProperty variance;
  private StringProperty critical;
  private StringProperty effects;
  private StringProperty note;

  public Skill(String id, String name, String iconIndex, String description, String stypeId,
      String scope, String mpCost, String tpCost, String occasion, String speed, String successRate,
      String repeats, String tpGain, String hitType, String animationId, String message1,
      String message2, String requiredWtypeId1, String requiredWtypeId2, String damageType,
      String damageElement, String formula, String variance, String critical, String effects,
      String note) {
    this.id = new SimpleStringProperty(id);
    this.name = new SimpleStringProperty(name);
    this.iconIndex = new SimpleStringProperty(iconIndex);
    this.description = new SimpleStringProperty(description);
    this.stypeId = new SimpleStringProperty(stypeId);
    this.scope = new SimpleStringProperty(scope);
    this.mpCost = new SimpleStringProperty(mpCost);
    this.tpCost = new SimpleStringProperty(tpCost);
    this.occasion = new SimpleStringProperty(occasion);
    this.speed = new SimpleStringProperty(speed);
    this.successRate = new SimpleStringProperty(successRate);
    this.repeats = new SimpleStringProperty(repeats);
    this.tpGain = new SimpleStringProperty(tpGain);
    this.hitType = new SimpleStringProperty(hitType);
    this.animationId = new SimpleStringProperty(animationId);
    this.message1 = new SimpleStringProperty(message1);
    this.message2 = new SimpleStringProperty(message2);
    this.requiredWtypeId1 = new SimpleStringProperty(requiredWtypeId1);
    this.requiredWtypeId2 = new SimpleStringProperty(requiredWtypeId2);
    this.damageType = new SimpleStringProperty(damageType);
    this.damageElement = new SimpleStringProperty(damageElement);
    this.formula = new SimpleStringProperty(formula);
    this.variance = new SimpleStringProperty(variance);
    this.critical = new SimpleStringProperty(critical);
    this.effects = new SimpleStringProperty(effects);
    this.note = new SimpleStringProperty(note);
  }

  public Skill(Skill skill) {
    this.id = new SimpleStringProperty(skill.idProperty().get());
    this.name = new SimpleStringProperty(skill.nameProperty().get());
    this.iconIndex = new SimpleStringProperty(skill.iconIndexProperty().get());
    this.description = new SimpleStringProperty(skill.descriptionProperty().get());
    this.stypeId = new SimpleStringProperty(skill.stypeIdProperty().get());
    this.scope = new SimpleStringProperty(skill.scopeProperty().get());
    this.mpCost = new SimpleStringProperty(skill.mpCostProperty().get());
    this.tpCost = new SimpleStringProperty(skill.tpCostProperty().get());
    this.occasion = new SimpleStringProperty(skill.occasionProperty().get());
    this.speed = new SimpleStringProperty(skill.speedProperty().get());
    this.successRate = new SimpleStringProperty(skill.successRateProperty().get());
    this.repeats = new SimpleStringProperty(skill.repeatsProperty().get());
    this.tpGain = new SimpleStringProperty(skill.tpGainProperty().get());
    this.hitType = new SimpleStringProperty(skill.hitTypeProperty().get());
    this.animationId = new SimpleStringProperty(skill.animationIdProperty().get());
    this.message1 = new SimpleStringProperty(skill.message1Property().get());
    this.message2 = new SimpleStringProperty(skill.message2Property().get());
    this.requiredWtypeId1 = new SimpleStringProperty(skill.requiredWtypeId1Property().get());
    this.requiredWtypeId2 = new SimpleStringProperty(skill.requiredWtypeId2Property().get());
    this.damageType = new SimpleStringProperty(skill.damageTypeProperty().get());
    this.damageElement = new SimpleStringProperty(skill.damageElementProperty().get());
    this.formula = new SimpleStringProperty(skill.formulaProperty().get());
    this.variance = new SimpleStringProperty(skill.varianceProperty().get());
    this.critical = new SimpleStringProperty(skill.criticalProperty().get());
    this.effects = new SimpleStringProperty(skill.effectsProperty().get());
    this.note = new SimpleStringProperty(skill.noteProperty().get());
  }

  // **************************************************
  // Getter
  // **************************************************
  public StringProperty idProperty() {
    return id;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty iconIndexProperty() {
    return iconIndex;
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public StringProperty stypeIdProperty() {
    return stypeId;
  }

  public StringProperty scopeProperty() {
    return scope;
  }

  public StringProperty mpCostProperty() {
    return mpCost;
  }

  public StringProperty tpCostProperty() {
    return tpCost;
  }

  public StringProperty occasionProperty() {
    return occasion;
  }

  public StringProperty speedProperty() {
    return speed;
  }

  public StringProperty successRateProperty() {
    return successRate;
  }

  public StringProperty repeatsProperty() {
    return repeats;
  }

  public StringProperty tpGainProperty() {
    return tpGain;
  }

  public StringProperty hitTypeProperty() {
    return hitType;
  }

  public StringProperty animationIdProperty() {
    return animationId;
  }

  public StringProperty message1Property() {
    return message1;
  }

  public StringProperty message2Property() {
    return message2;
  }

  public StringProperty requiredWtypeId1Property() {
    return requiredWtypeId1;
  }

  public StringProperty requiredWtypeId2Property() {
    return requiredWtypeId2;
  }

  public StringProperty damageTypeProperty() {
    return damageType;
  }

  public StringProperty damageElementProperty() {
    return damageElement;
  }

  public StringProperty formulaProperty() {
    return formula;
  }

  public StringProperty varianceProperty() {
    return variance;
  }

  public StringProperty criticalProperty() {
    return critical;
  }

  public StringProperty effectsProperty() {
    return effects;
  }

  public StringProperty noteProperty() {
    return note;
  }

  // **************************************************
  // Setter
  // **************************************************
  public void setId(String id) {
    this.id = new SimpleStringProperty(id);
  }

  public void setName(String name) {
    this.name = new SimpleStringProperty(name);
  }

  public void setIconIndex(String iconIndex) {
    this.iconIndex = new SimpleStringProperty(iconIndex);
  }

  public void setDescription(String description) {
    this.description = new SimpleStringProperty(description);
  }

  public void setStypeId(String stypeId) {
    this.stypeId = new SimpleStringProperty(stypeId);
  }

  public void setScope(String scope) {
    this.scope = new SimpleStringProperty(scope);
  }

  public void setMpCost(String mpCost) {
    this.mpCost = new SimpleStringProperty(mpCost);
  }

  public void setTpCost(String tpCost) {
    this.tpCost = new SimpleStringProperty(tpCost);
  }

  public void setOccasion(String occasion) {
    this.occasion = new SimpleStringProperty(occasion);
  }

  public void setSpeed(String speed) {
    this.speed = new SimpleStringProperty(speed);
  }

  public void setSuccessRate(String successRate) {
    this.successRate = new SimpleStringProperty(successRate);
  }

  public void setRepeats(String repeats) {
    this.repeats = new SimpleStringProperty(repeats);
  }

  public void setTpGain(String tpGain) {
    this.tpGain = new SimpleStringProperty(tpGain);
  }

  public void setHitType(String hitType) {
    this.hitType = new SimpleStringProperty(hitType);
  }

  public void setAnimationId(String animationId) {
    this.animationId = new SimpleStringProperty(animationId);
  }

  public void setMessage1(String message1) {
    this.message1 = new SimpleStringProperty(message1);
  }

  public void setMessage2(String message2) {
    this.message2 = new SimpleStringProperty(message2);
  }

  public void setRequiredWtypeId1(String requiredWtypeId1) {
    this.requiredWtypeId1 = new SimpleStringProperty(requiredWtypeId1);
  }

  public void setRequiredWtypeId2(String requiredWtypeId2) {
    this.requiredWtypeId2 = new SimpleStringProperty(requiredWtypeId2);
  }

  public void setDamageType(String damageType) {
    this.damageType = new SimpleStringProperty(damageType);
  }

  public void setDamageElement(String damageElement) {
    this.damageElement = new SimpleStringProperty(damageElement);
  }

  public void setFormula(String formula) {
    this.formula = new SimpleStringProperty(formula);
  }

  public void setVariance(String variance) {
    this.variance = new SimpleStringProperty(variance);
  }

  public void setCritical(String critical) {
    this.critical = new SimpleStringProperty(critical);
  }

  public void setEffects(String effects) {
    this.effects = new SimpleStringProperty(effects);
  }

  public void setNote(String note) {
    this.note = new SimpleStringProperty(note);
  }
}
