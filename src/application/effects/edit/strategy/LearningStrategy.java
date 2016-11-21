package application.effects.edit.strategy;

import java.util.List;

import javafx.scene.control.ListView;

class LearningStrategy extends EditStrategy {
  private List<String> skillList;
  private ListView<String> listView;

  public LearningStrategy(List<String> skillList) {
    this.skillList = skillList;
  }

  public LearningStrategy(ListView<String> aListView) {
    listView = aListView;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return skillList.get(dataId);
  }

  @Override
  String convertJsonString() {
    return null;
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    listView.getSelectionModel().select(dataId);
  }
}
