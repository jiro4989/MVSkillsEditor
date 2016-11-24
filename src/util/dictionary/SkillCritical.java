package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SkillCritical implements SkillTableData {
    BOOL_TRUE("あり"),
    BOOL_FALSE("なし");

  private final String text;

  private SkillCritical(String aText) {
    text = aText;
  }

  public static List<String> getNameList() {
    return Arrays.stream(SkillCritical.values())
        .map(s -> s.text)
        .collect(Collectors.toList());
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param aText true or false
   * @return あり or なし
   */
  public static String convertToText(boolean bool) {
    return bool ? BOOL_TRUE.text : BOOL_FALSE.text;
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param aText あり or なし
   * @return true or false
   */
  public static boolean convertToBoolean(String aText) {
    return BOOL_TRUE.text.equals(aText);
  }

  @Override
  public String getText() {
    return text;
  }
}
