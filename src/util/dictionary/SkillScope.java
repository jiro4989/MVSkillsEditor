package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 範囲の辞書クラス。
 * @author jiro
 */
public enum SkillScope implements SkillTableData {
    NON("なし"),
    ENEMY_SINGLE("敵単体"),
    ENEMY_ALL("敵全体"),
    ENEMY_RANDOM1("敵1体 ランダム"),
    ENEMY_RANDOM2("敵2体 ランダム"),
    ENEMY_RANDOM3("敵3体 ランダム"),
    ENEMY_RANDOM4("敵4体 ランダム"),
    ACTOR_SINGLE("味方単体"),
    ACTOR_ALL("味方全体"),
    ACTOR_SINGLE_DEAD("味方単体 (戦闘不能)"),
    ACTOR_ALL_DEAD("味方全体 (戦闘不能)"),
    OWN("使用者");

  private final String text;

  private SkillScope(String aText) {
    text = aText;
  }

  public static List<String> getNameList() {
    return Arrays.stream(SkillScope.values())
        .map(s -> s.text)
        .collect(Collectors.toList());
  }

  public static ObservableList<String> getObservableList() {
    return FXCollections.observableArrayList(getNameList());
  }

  /**
   * Jsonでのインデックスを対応するテキストに変換する。
   * @param index scopeIndex
   * @return 対応するテキスト
   */
  public static String convertToText(int index) {
    for (SkillScope scope : SkillScope.values()) {
      if (index == scope.ordinal()) {
        return scope.text;
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
    for (SkillScope scope : SkillScope.values()) {
      if (scope.text.equals(aText)) {
        return scope.ordinal();
      }
    }
    return 0;
  }

  @Override
  public String getText() {
    return text;
  }
}
