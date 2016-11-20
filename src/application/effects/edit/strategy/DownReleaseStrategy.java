package application.effects.edit.strategy;

class DownReleaseStrategy extends EditStrategy {

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Parameters.values()[dataId].name();
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
