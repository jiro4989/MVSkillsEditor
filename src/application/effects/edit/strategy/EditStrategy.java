package application.effects.edit.strategy;

/**
 * 使用効果のテキストを編集する戦略インタフェース。
 * @author jiro
 *
 */
public interface EditStrategy {
  public String formatContent(int codeId, int dataId, double value1, double value2);
}
