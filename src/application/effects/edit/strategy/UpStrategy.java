package application.effects.edit.strategy;

class UpStrategy extends EditStrategy {

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Parameters.values()[dataId].name() + " " + (int) value1 + " ターン";
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
