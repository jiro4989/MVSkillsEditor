package application.effects.edit.strategy;

import java.util.List;

import application.effects.edit.ListViewManager;
import javafx.scene.control.ListView;

class CommonEventStrategy extends EditStrategy {
  private List<String> commonEventList;
  private ListViewManager manager;

  public CommonEventStrategy(List<String> commonEventList) {
    this.commonEventList = commonEventList;
  }

  public CommonEventStrategy(ListViewManager aManager) {
    manager = aManager;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return commonEventList.get(dataId);
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    manager.getListView().getSelectionModel().select(dataId);
  }

  @Override
  double[] getValues() {
    return manager.getValue(44.0);
  }

  @Override
  void changeDisable() {
    ListView<String> listView = manager.getListView();
    int currentIndex = listView.getSelectionModel().isEmpty()
        ? 0 : listView.getSelectionModel().getSelectedIndex();
    listView.getSelectionModel().select(currentIndex);
    manager.setDisable(false);
  }
}
