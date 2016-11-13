package application;

import com.sun.glass.ui.TouchInputSupport;

public enum SkillKeys {
  ID("id", "ID"),
  NAME("name", "名前"),
  ICON("iconIndex", "アイコン"),
  DESCRIPTION("description", "解説"),
  STYPE_ID("stypeId", "スキルタイプ"),
  SCOPE("scope", "範囲"),
  MP_COST("mpCost", "消費MP"),
  TP_COST("tpCost", "消費TP"),
  OCCASION("occasion", "使用可能時"),
  SPEED("speed", "速度補正"),
  SUCCESS_RATE("successRate", "成功率"),
  REPEATS("repeats", "連続回数"),
  TP_GAIN("tpGain", "得TP"),
  HIT_TYPE("hitType", "命中タイプ"),
  ANIMATION_ID("animationId", "アニメーション"),
  MESSAGE1("message1", "メッセージ1"),
  MESSAGE2("message2", "メッセージ2"),
  REQUIRED1("requiredWtypeId1", "必要武器1"),
  REQUIRED2("requiredWtypeId2", "必要武器2"),

  DAMAGE("damage", ""),
  DAMAGE_TYPE("type", "ダメージタイプ"),
  DAMAGE_ELEMENT("elementId", "属性"),
  FORMULA("formula", "計算式"),
  VARIANCE("variance", "分散度"),
  CRITICAL("critical", "会心率"),

  EFFECTS("effects", "使用効果"),
  NOTE("note", "メモ");

  private SkillKeys(String key, String columnName) {
    this.key = key;
    this.columnName = columnName;
  }

  private final String key;
  private final String columnName;

  public String getKey() {
    return key;
  }
  public String getColumnName() {
    return columnName;
  }
}
