package application.effects.edit.strategy;

/**
 * 使用効果のテキストを編集する戦略インタフェース。
 * @author jiro
 */
abstract class EditStrategy {
  /**
   * IDと値から表示上のテキストに整形する。
   * @param codeId CodeId
   * @param dataId DataId
   * @param value1 Value1
   * @param value2 Value2
   * @return 整形後の文字列
   */
  abstract String formatToContentText(int codeId, int dataId, double value1, double value2);

  /**
   * セットした値を返す。
   * @return
   *     values[0] = code<br>
   *     values[1] = dataId<br>
   *     values[2] = value1<br>
   *     values[3] = value2<br>
   */
  abstract double[] getValues();

  /**
   * コンポーネントに値をセットする。
   * @param dataId DataId
   * @param value1 Value1
   * @param value2 Value2
   */
  abstract void setValue(int dataId, double value1, double value2);

  /**
   * 他のコンポーネントをすべて非表示にする。
   */
  abstract void changeDisable();
}
