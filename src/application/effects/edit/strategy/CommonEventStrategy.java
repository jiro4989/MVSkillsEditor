package application.effects.edit.strategy;

import java.util.List;

class CommonEventStrategy extends EditStrategy {
  private List<String> commonEventList;

  public CommonEventStrategy(List<String> commonEventList) {
    this.commonEventList = commonEventList;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return commonEventList.get(dataId);
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
