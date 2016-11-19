package application.effects;

/**
 * 使用効果プレビュー画面で表示される説明文と、
 * JSONファイルでのコードIDを管理するenumクラス。
 * CodeIDを画面表示上のテキストに変換する目的で利用する。
 * @author jiro
 */
public enum EffectsCodes {
  C11("HP回復", 11), C12("MP回復", 12), C13("TP回復", 13), C21("ステート付加", 21), C22("ステート解除", 22), C31("強化",
      31), C32("弱体", 32), C33("強化の解除", 33), C34("弱体の解除",
          34), C41("特殊効果", 41), C42("成長", 42), C43("スキルの習得", 43), C44("コモンイベント", 44);

  private EffectsCodes(String codeText, int codeId) {
    this.codeText = codeText;
    this.codeId = codeId;
  }

  private final String codeText;
  private final int codeId;

  /**
   * 引数で渡したEffectsCodeのIDと一致したEffectsCodesを返す。
   * @param id JSONファイルで使用されているCodeID
   * @return 一致したEffectsCodes、見つからなかった場合はnull
   */
  public static EffectsCodes getEfectsCode(int id) {
    for (EffectsCodes code : EffectsCodes.values()) {
      if (code.getCodeId() == id) {
        return code;
      }
    }
    return null;
  }

  /**
   * 引数で渡した表示上のテキストと一致したEffectsCodeを返す。
   * @param text EffectsCodeのテキスト
   * @return 一致したEffectsCode、見つからなかった場合はnull
   */
  public static EffectsCodes getEfectsCode(String text) {
    for (EffectsCodes code : EffectsCodes.values()) {
      if (code.getCodeText().equals(text)) {
        return code;
      }
    }
    return null;
  }

  /**
   * CodeIdを対応するテキストに変換する。
   * @param id CodeId
   * @return テキスト
   */
  public static String convertCodeIdToCodeText(int id) {
    return getEfectsCode(id).getCodeText();
  }

  /**
   * Textを対応するCodeIdに変換する。
   * @param text Text
   * @return CodeId
   */
  public static int convertCodeTextToCodeId(String text) {
    return getEfectsCode(text).getCodeId();
  }

  public String getCodeText() {
    return codeText;
  }

  public int getCodeId() {
    return codeId;
  }
}
