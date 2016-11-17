package command;

/**
 * UndoRedoを実装するためのコマンドクラス。
 * @author jiro
 */
public interface ICommand {
  /**
   * 実行する。
   */
  public void invoke();

  /**
   * 処理を元に戻す。
   */
  public void undo();

  /**
   * 処理をやり直す。
   */
  public void redo();
}
