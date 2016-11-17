package strategy;

import application.Skill;
import javafx.scene.control.TableView;

/**
 * カラムごとの戦略インタフェース
 * @author jiro
 */
public abstract class ColumnStrategy {
  protected TableView<Skill> tableView;
  protected int rowIndex;

  /**
   * 値をObjectクラスで取得する。
   * @return Object value
   */
  public abstract Object getValue();

  /**
   * 値をセットする。 実装クラスは必要な方にキャストして使用しなければならない。
   * @param value
   *          セットするObjectValue
   */
  public abstract void setValue(Object value);
}
