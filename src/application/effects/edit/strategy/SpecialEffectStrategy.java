package application.effects.edit.strategy;

class SpecialEffectStrategy extends EditStrategy {

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return "逃げる";
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
