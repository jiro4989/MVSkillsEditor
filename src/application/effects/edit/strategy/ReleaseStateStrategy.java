package application.effects.edit.strategy;

import java.util.List;

import application.effects.edit.ListViewManager;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

class ReleaseStateStrategy extends EditStrategy {
  private List<String> stateList;
  private TextField textField;
  private ListViewManager manager;

  public ReleaseStateStrategy(List<String> aStateList) {
    stateList = aStateList;
  }

  public ReleaseStateStrategy(ListViewManager aManager, TextField aTextField) {
    manager = aManager;
    textField = aTextField;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    String stateName = stateList.get(dataId);
    return stateName + " " + (int) (value1 * 100) + " %";
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    manager.getListView().getSelectionModel().select(dataId);
    textField.setText("" + (int) (value1 * 100));
  }

  @Override
  double[] getValues() {
    double[] values = manager.getValue(22.0);
    values[2] = Double.parseDouble(textField.getText()) / 100;
    return values;
  }

  @Override
  void changeDisable() {
    ListView<String> listView = manager.getListView();
    int currentIndex = listView.getSelectionModel().isEmpty()
        ? 0 : listView.getSelectionModel().getSelectedIndex();
    listView.getSelectionModel().select(currentIndex);
    manager.setDisable(false);
    textField.setDisable(false);
  }
}
