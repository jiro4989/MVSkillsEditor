package application.effects.edit.strategy;

import javafx.scene.Node;
import javafx.scene.control.TextField;

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
   * 保持する値をJson文字列に変換する。
   * @return Json文字列
   */
  abstract String convertJsonString();

  /**
   * コンポーネントに値をセットする。
   * @param dataId DataId
   * @param value1 Value1
   * @param value2 Value2
   */
  abstract void setValue(int dataId, double value1, double value2);
}
