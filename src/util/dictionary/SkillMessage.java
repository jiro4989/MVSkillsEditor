package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SkillMessage implements SkillTableData {
  CHANT("は%1を唱えた！"),
  SEND("は%1を放った！"),
  USE("は%1を使った！");

  private final String text;

  private SkillMessage(String aText) {
    text = aText;
  }

  public static List<String> getNameList() {
    return Arrays.stream(SkillMessage.values())
        .map(s -> s.text)
        .collect(Collectors.toList());
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param index scopeIndex
   * @return 対応するテキスト
   */
  public static String convertToText(int index) {
    for (SkillMessage value : SkillMessage.values()) {
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
    for (SkillMessage value : SkillMessage.values()) {
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
