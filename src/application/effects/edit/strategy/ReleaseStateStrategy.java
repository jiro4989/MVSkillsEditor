package application.effects.edit.strategy;

import java.util.List;

class ReleaseStateStrategy extends EditStrategy {
  private List<String> stateList;

  public ReleaseStateStrategy(List<String> aStateList) {
    stateList = aStateList;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    String stateName = stateList.get(dataId);
    return stateName + " " + (int) (value1 * 100) + " %";
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
