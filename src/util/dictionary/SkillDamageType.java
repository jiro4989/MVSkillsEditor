package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SkillDamageType implements SkillTableData {
  NON("なし"),
  HP_DAMAGE("HPダメージ"),
  MP_DAMAGE("MPダメージ"),
  HP_HEAL("HP回復"),
  MP_HEAL("MP回復"),
  HP_ABSORB("HP吸収"),
  MP_ABSORB("MP吸収");

  private final String text;

  private SkillDamageType(String aText) {
    text = aText;
  }

  public static List<String> getNameList() {
    return Arrays.stream(SkillDamageType.values())
        .map(s -> s.text)
        .collect(Collectors.toList());
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param index scopeIndex
   * @return 対応するテキスト
   */
  public static String convertToText(int index) {
    for (SkillDamageType value : SkillDamageType.values()) {
      if (index == value.ordinal()) {
        return value.text;
      }
    }
    return null;
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param aText text
   * @return 対応するインデックス
   */
  public static int convertToIndex(String aText) {
    for (SkillDamageType value : SkillDamageType.values()) {
      if (value.text.equals(aText)) {
        return value.ordinal();
      }
    }
    return -1;
  }

  @Override
  public String getText() {
    return text;
  }
}
