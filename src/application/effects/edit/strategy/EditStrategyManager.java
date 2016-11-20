package application.effects.edit.strategy;

import java.util.List;

/**
 * 戦略クラスの切り替えと処理の実行を管理するクラス。
 * @author jiro
 */
public class EditStrategyManager {
  private EditStrategy strategy;

  /**
   * 表示用のテキストに整形して返す。
   * @param codeId
   * @param dataId
   * @param value1
   * @param value2
   * @return 表示用テキスト
   */
  public String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return strategy.formatToContentText(codeId, dataId, value1, value2);
  }

  /**
   * Json文字列に変換して返す。
   * @return Json文字列
   */
  public String convertJsonString() {
    return strategy.convertJsonString();
  }

  /**
   * 戦略インスタンスを変更する。
   * @param codeId
   * @param skillList
   * @param stateList
   * @param commonEventList
   */
  public void changeStrategy(int codeId, List<String> skillList, List<String> stateList,
      List<String> commonEventList) {
    int strategyIndex = getStrategyIndexFrom(codeId);

    switch (strategyIndex) {
    case 0:
      strategy = new HPHealStrategy();
      break;
    case 1:
      strategy = new MPHealStrategy();
      break;
    case 2:
      strategy = new TPHealStrategy();
      break;
    case 3:
      strategy = new AddStateStrategy(stateList);
      break;
    case 4:
      strategy = new ReleaseStateStrategy(stateList);
      break;
    case 5:
      strategy = new UpStrategy();
      break;
    case 6:
      strategy = new DownStrategy();
      break;
    case 7:
      strategy = new UpReleaseStrategy();
      break;
    case 8:
      strategy = new DownReleaseStrategy();
      break;
    case 9:
      strategy = new SpecialEffectStrategy();
      break;
    case 10:
      strategy = new GrowthStrategy();
      break;
    case 11:
      strategy = new LearningStrategy(skillList);
      break;
    case 12:
      strategy = new CommonEventStrategy(commonEventList);
      break;
    default:
      break;
    }
  }

  /**
   * 戦略クラスのインスタンス切り替えのためのインデックスを取得する。
   * @param codeId CodeId
   * @return 戦略インデックス
   */
  private int getStrategyIndexFrom(int codeId) {
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
}
