package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SkillOccassion implements SkillTableData {
  ALWAYS("常時"),
  BATTLE("バトル画面"),
  MENU("メニュー画面"),
  DISABLED("使用不可");

  private final String text;

  private SkillOccassion(String aText) {
    text = aText;
  }

  public static List<String> getNameList() {
    return Arrays.stream(SkillOccassion.values())
        .map(s -> s.text)
        .collect(Collectors.toList());
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param index scopeIndex
   * @return 対応するテキスト
   */
  public static String convertToText(int index) {
    for (SkillOccassion value : SkillOccassion.values()) {
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
    for (SkillOccassion value : SkillOccassion.values()) {
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
