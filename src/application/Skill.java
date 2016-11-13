package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Skill {
  private IntegerProperty id;
  private StringProperty name;
  private IntegerProperty iconIndex;
  private StringProperty description;
  private IntegerProperty stypeId;
  private IntegerProperty scope;
  private IntegerProperty mpCost;
  private IntegerProperty tpCost;
  private IntegerProperty occasion;
  private IntegerProperty speed;
  private IntegerProperty successRate;
  private IntegerProperty repeats;
  private IntegerProperty tpGain;
  private IntegerProperty hitType;
  private IntegerProperty animationId;
  private StringProperty message1;
  private StringProperty message2;
  private IntegerProperty requiredWtypeId1;
  private IntegerProperty requiredWtypeId2;
  private IntegerProperty damageType;
  private IntegerProperty damageElement;
  private StringProperty formula;
  private IntegerProperty variance;
  private IntegerProperty critical;
  private StringProperty effects;
  private StringProperty note;

  public Skill(int id, String name, int iconIndex, String description, int stypeId, int scope,
      int mpCost, int tpCost, int occasion, int speed, int successRate, int repeats, int tpGain,
      int hitType, int animationId, String message1, String message2, int requiredWtypeId1,
      int requiredWtypeId2, int damageType, int damageElement, String formula, int variance,
      int critical, String effects, String note) {
    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.iconIndex = new SimpleIntegerProperty(iconIndex);
    this.description = new SimpleStringProperty(description);
    this.stypeId = new SimpleIntegerProperty(stypeId);
    this.scope = new SimpleIntegerProperty(scope);
    this.mpCost = new SimpleIntegerProperty(mpCost);
    this.tpCost = new SimpleIntegerProperty(tpCost);
    this.occasion = new SimpleIntegerProperty(occasion);
    this.speed = new SimpleIntegerProperty(speed);
    this.successRate = new SimpleIntegerProperty(successRate);
    this.repeats = new SimpleIntegerProperty(repeats);
    this.tpGain = new SimpleIntegerProperty(tpGain);
    this.hitType = new SimpleIntegerProperty(hitType);
    this.animationId = new SimpleIntegerProperty(animationId);
    this.message1 = new SimpleStringProperty(message1);
    this.message2 = new SimpleStringProperty(message2);
    this.requiredWtypeId1 = new SimpleIntegerProperty(requiredWtypeId1);
    this.requiredWtypeId2 = new SimpleIntegerProperty(requiredWtypeId2);
    this.damageType = new SimpleIntegerProperty(damageType);
    this.damageElement = new SimpleIntegerProperty(damageElement);
    this.formula = new SimpleStringProperty(formula);
    this.variance = new SimpleIntegerProperty(variance);
    this.critical = new SimpleIntegerProperty(critical);
    this.effects = new SimpleStringProperty(effects);
    this.note = new SimpleStringProperty(note);
  }

  // **************************************************
  // Getter
  // **************************************************
  public IntegerProperty idProperty() {
    return id;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public IntegerProperty iconIndexProperty() {
    return iconIndex;
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public IntegerProperty stypeIdProperty() {
    return stypeId;
  }

  public IntegerProperty scopeProperty() {
    return scope;
  }

  public IntegerProperty mpCostProperty() {
    return mpCost;
  }

  public IntegerProperty tpCostProperty() {
    return tpCost;
  }

  public IntegerProperty occasionProperty() {
    return occasion;
  }

  public IntegerProperty speedProperty() {
    return speed;
  }

  public IntegerProperty successRateProperty() {
    return successRate;
  }

  public IntegerProperty repeatsProperty() {
    return repeats;
  }

  public IntegerProperty tpGainProperty() {
    return tpGain;
  }

  public IntegerProperty hitTypeProperty() {
    return hitType;
  }

  public IntegerProperty animationIdProperty() {
    return animationId;
  }

  public StringProperty message1Property() {
    return message1;
  }

  public StringProperty message2Property() {
    return message2;
  }

  public IntegerProperty requiredWtypeId1Property() {
    return requiredWtypeId1;
  }

  public IntegerProperty requiredWtypeId2Property() {
    return requiredWtypeId2;
  }

  public IntegerProperty damageTypeProperty() {
    return damageType;
  }

  public IntegerProperty damageElementProperty() {
    return damageElement;
  }

  public StringProperty formulaProperty() {
    return formula;
  }

  public IntegerProperty varianceProperty() {
    return variance;
  }

  public IntegerProperty criticalProperty() {
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
  public void setId(int id) {
    this.id = new SimpleIntegerProperty(id);
  }

  public void setName(String name) {
    this.name = new SimpleStringProperty(name);
  }

  public void setIconIndex(int iconIndex) {
    this.iconIndex = new SimpleIntegerProperty(iconIndex);
  }

  public void setDescription(String description) {
    this.description = new SimpleStringProperty(description);
  }

  public void setStypeId(int stypeId) {
    this.stypeId = new SimpleIntegerProperty(stypeId);
  }

  public void setScope(int scope) {
    this.scope = new SimpleIntegerProperty(scope);
  }

  public void setMpCost(int mpCost) {
    this.mpCost = new SimpleIntegerProperty(mpCost);
  }

  public void setTpCost(int tpCost) {
    this.tpCost = new SimpleIntegerProperty(tpCost);
  }

  public void setOccasion(int occasion) {
    this.occasion = new SimpleIntegerProperty(occasion);
  }

  public void setSpeed(int speed) {
    this.speed = new SimpleIntegerProperty(speed);
  }

  public void setSuccessRate(int successRate) {
    this.successRate = new SimpleIntegerProperty(successRate);
  }

  public void setRepeats(int repeats) {
    this.repeats = new SimpleIntegerProperty(repeats);
  }

  public void setTpGain(int tpGain) {
    this.tpGain = new SimpleIntegerProperty(tpGain);
  }

  public void setHitType(int hitType) {
    this.hitType = new SimpleIntegerProperty(hitType);
  }

  public void setAnimationId(int animationId) {
    this.animationId = new SimpleIntegerProperty(animationId);
  }

  public void setMessage1(String message1) {
    this.message1 = new SimpleStringProperty(message1);
  }

  public void setMessage2(String message2) {
    this.message2 = new SimpleStringProperty(message2);
  }

  public void setRequiredWtypeId1(int requiredWtypeId1) {
    this.requiredWtypeId1 = new SimpleIntegerProperty(requiredWtypeId1);
  }

  public void setRequiredWtypeId2(int requiredWtypeId2) {
    this.requiredWtypeId2 = new SimpleIntegerProperty(requiredWtypeId2);
  }

  public void setDamageType(int damageType) {
    this.damageType = new SimpleIntegerProperty(damageType);
  }

  public void setDamageElement(int damageElement) {
    this.damageElement = new SimpleIntegerProperty(damageElement);
  }

  public void setFormula(String formula) {
    this.formula = new SimpleStringProperty(formula);
  }

  public void setVariance(int variance) {
    this.variance = new SimpleIntegerProperty(variance);
  }

  public void setCritical(int critical) {
    this.critical = new SimpleIntegerProperty(critical);
  }

  public void setEffects(String effects) {
    this.effects = new SimpleStringProperty(effects);
  }

  public void setNote(String note) {
    this.note = new SimpleStringProperty(note);
  }
}
