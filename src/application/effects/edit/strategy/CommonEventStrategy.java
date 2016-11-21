package application.effects.edit.strategy;

import java.util.List;

import javafx.scene.control.ListView;

class CommonEventStrategy extends EditStrategy {
  private List<String> commonEventList;
  private ListView<String> listView;

  public CommonEventStrategy(List<String> commonEventList) {
    this.commonEventList = commonEventList;
  }

  public CommonEventStrategy(ListView<String> aListView) {
    listView = aListView;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return commonEventList.get(dataId);
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
