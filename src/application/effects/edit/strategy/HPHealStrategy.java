package application.effects.edit.strategy;

class HPHealStrategy extends EditStrategy {

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    if ((int) value2 == 0) {
      return (int) (value1 * 100) + " %";
    }
    return (int) (value1 * 100) + " %" + " ＋ " + (int) value2;
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
