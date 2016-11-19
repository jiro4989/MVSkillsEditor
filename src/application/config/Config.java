package application.config;

/**
 * アプリケーションの設定情報などを保持するだけのクラス。
 * @author jiro
 *
 */
public class Config {
  public String projectPath;
  public String inputPath;

  @Override
  public String toString() {
    return String.format("projectPath: %s, inputPath: %s", projectPath, inputPath);
  }
}
