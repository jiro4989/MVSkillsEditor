package util.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CodeId31から34で使用するパラメータテキスト
 * @author jiro
 */
public enum Ability implements SkillTableData {
    MHP("最大HP"),
    MMP("最大MP"),
    ATK("攻撃力"),
    DEF("防御力"),
    MAT("魔法力"),
    MDF("魔法防御"),
    AGI("敏捷性"),
    LUK("運");

  private final String text;

  private Ability(String aText) {
    text = aText;
  }

  @Override
  public String getText() {
    return text;
  }

  public static List<String> getNameList() {
    return Arrays.stream(Ability.values())
        .map(a -> a.text)
        .collect(Collectors.toList());
  }

  public static ObservableList<String> getObservableList() {
    return FXCollections.observableArrayList(getNameList());
  }
}
