package application.effects.edit.strategy;

import java.util.List;

import application.effects.edit.ListViewManager;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
   * セットした値を返す。
   * @return
   *         values[0] = code<br>
   *         values[1] = dataId<br>
   *         values[2] = value1<br>
   *         values[3] = value2<br>
   */
  public double[] getValues() {
    return strategy.getValues();
  }

  /**
   * コンポーネントに値をセットする。
   * @param dataId
   * @param value1
   * @param value2
   */
  public void setValues(int dataId, double value1, double value2) {
    strategy.setValue(dataId, value1, value2);
  }

  /**
   * 戦略インスタンスを変更する。
   * @param codeId
   * @param skillList
   * @param stateList
   * @param commonEventList
   */
  public void changeStrategy(
      int strategyIndex,
      List<String> skillList,
      List<String> stateList,
      List<String> commonEventList) {
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
   * 戦略インスタンスを変更する。
   * @param codeId
   * @param hptf1
   * @param hptf2
   * @param mptf1
   * @param mptf2
   * @param tptf
   * @param slv
   * @param stf
   * @param stf2
   * @param upcb
   * @param uptf
   * @param downcb
   * @param downtf
   * @param upcb2
   * @param downcb2
   * @param specialcb
   * @param growthcb
   * @param growthtf
   * @param llv
   * @param celv
   */
  public void changeStrategy(
      int strategyIndex,
      TextField hptf1, TextField hptf2,
      TextField mptf1, TextField mptf2,
      TextField tptf,
      ListViewManager addStateManager, TextField stf,
      ListViewManager releaseStateManager, TextField stf2,
      ComboBox<String> upcb, TextField uptf,
      ComboBox<String> downcb, TextField downtf,
      ComboBox<String> upcb2,
      ComboBox<String> downcb2,
      ComboBox<String> specialcb,
      ComboBox<String> growthcb, TextField growthtf,
      ListViewManager learningManager,
      ListViewManager commonEventManager) {
    switch (strategyIndex) {
    case 0:
      strategy = new HPHealStrategy(hptf1, hptf2);
      break;
    case 1:
      strategy = new MPHealStrategy(mptf1, mptf2);
      break;
    case 2:
      strategy = new TPHealStrategy(tptf);
      break;
    case 3:
      strategy = new AddStateStrategy(addStateManager, stf);
      break;
    case 4:
      strategy = new ReleaseStateStrategy(releaseStateManager, stf2);
      break;
    case 5:
      strategy = new UpStrategy(upcb, uptf);
      break;
    case 6:
      strategy = new DownStrategy(downcb, downtf);
      break;
    case 7:
      strategy = new UpReleaseStrategy(upcb2);
      break;
    case 8:
      strategy = new DownReleaseStrategy(downcb2);
      break;
    case 9:
      strategy = new SpecialEffectStrategy(specialcb);
      break;
    case 10:
      strategy = new GrowthStrategy(growthcb, growthtf);
      break;
    case 11:
      strategy = new LearningStrategy(learningManager);
      break;
    case 12:
      strategy = new CommonEventStrategy(commonEventManager);
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
  public int calculateStrategyIndex(int codeId) {
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

  public void changeDisable() {
    strategy.changeDisable();
  }
}
