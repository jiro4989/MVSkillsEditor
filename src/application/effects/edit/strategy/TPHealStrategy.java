package application.effects.edit.strategy;

class TPHealStrategy extends EditStrategy {

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return "" + (int) value1;
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
