package application.tableview.strategy.record;

import application.tableview.Skill;
import javafx.scene.control.TableView;

/**
 * 行データの操作戦略クラス
 * @author jiro
 */
public abstract class RecordStrategy {
  protected TableView<Skill> tableView;
  protected int rowIndex;

  public abstract void execute();

  /**
   * 実行可能かどうかを返す。
   * @return true or false
   */
  public abstract boolean isInvokable(Object value);

  public static Skill getDefaultSkillRecord() {
    // 途中
    return new Skill("0", "", "0", "", "00: なし", "敵単体", "0", "0", "常時",
        "0", "100", "1", "0", "必中", "0000: なし", "", "",
        "00: なし", "00: なし", "なし", "00: なし", "0", "20", "なし",
        "[]", "");
  }
}
