package application.effects.edit.strategy;

import java.util.List;

class LearningStrategy extends EditStrategy {
  private List<String> skillList;

  public LearningStrategy(List<String> skillList) {
    this.skillList = skillList;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return skillList.get(dataId);
  }

  @Override
  String convertJsonString() {
    return null;
  }

}
