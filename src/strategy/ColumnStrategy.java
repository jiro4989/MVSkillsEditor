package strategy;

public interface ColumnStrategy {
  /**
   * 値をObjectクラスで取得する。
   * @return Object value
   */
  public Object getValue();

  /**
   * 値をセットする。
   * 実装クラスは必要な方にキャストして使用しなければならない。
   * @param value セットするObjectValue
   */
  public void setValue(Object value);
}
