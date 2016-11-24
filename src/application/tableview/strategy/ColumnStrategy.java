package application.tableview.strategy;

import application.tableview.Skill;
import javafx.scene.control.TableView;

/**
 * カラムごとの戦略インタフェース
 * @author jiro
 */
public abstract class ColumnStrategy {
  protected TableView<Skill> tableView;
  protected int rowIndex;
  protected static final String NUMBER_REGEX = "^[-]*[0-9]+";

  /**
   * 値をObjectクラスで取得する。
   * @return Object value
   */
  public abstract Object getValue();

  /**
   * 値をセットする。 実装クラスは必要な方にキャストして使用しなければならない。
   * @param value セットするObjectValue
   */
  public abstract void setValue(Object value);

  /**
   * 実行可能かどうかを返す。
   * @return true or false
   */
  public abstract boolean isInvokable(Object value);

  /**
   * 実行不可能だった場合に設定するデフォルト値
   * @param value デフォルト値に変換する文字列
   * @return 変換後の文字列、またはデフォルト値が存在しない場合はnull
   */
  public abstract String defaultValue(String value);

  /**
   * 数値を扱う戦略クラスが利用する上限、下限を超えた数値を修正するメソッド。
   * @return 修正後の文字、または数値でなかった場合はnull
   */
  protected String numberFixer(String value, int min, int max) {
    if (value.matches(NUMBER_REGEX)) {
      int number = Integer.parseInt(value);
      number = number < min ? min : max;
      return "" + number;
    }
    return null;
  }
}
