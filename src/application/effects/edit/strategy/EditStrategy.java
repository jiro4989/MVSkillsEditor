package application.effects.edit.strategy;

/**
 * 使用効果のテキストを編集する戦略インタフェース。
 * @author jiro
 */
public abstract class EditStrategy {
  public abstract String formatToContentText(int codeId, int dataId, double value1, double value2);
  public abstract String convertJsonString();

  /**
   * 戦略クラスのインスタンス切り替えのためのインデックスを取得する。
   * @param codeId CodeId
   * @return 戦略インデックス
   */
  public static int getStrategyIndexFrom(int codeId) {
    int placeTen = codeId / 10 - 1;
    int placeOne = codeId % 10 - 1;
    // @formatter:off
    placeOne =
          placeTen == 0 ? placeOne
        : placeTen == 1 ? placeOne + 3
        : placeTen == 2 ? placeOne + 5
        : placeOne + 9;
    // @formatter:on
    return placeOne;
  }

  public static void changeStrategy() {

  }
}
