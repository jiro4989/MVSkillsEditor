package application.effects.edit.strategy;

import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

class ReleaseStateStrategy extends EditStrategy {
  private List<String> stateList;
  private ListView<String> stateListView;
  private TextField releaseStateTextField;

  public ReleaseStateStrategy(List<String> aStateList) {
    stateList = aStateList;
  }

  public ReleaseStateStrategy(ListView<String> aListView, TextField aTextField) {
    stateListView = aListView;
    releaseStateTextField = aTextField;
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

  @Override
  void setValue(int dataId, double value1, double value2) {
    stateListView.getSelectionModel().select(dataId);
    releaseStateTextField.setText("" + (int) (value1 * 100));
  }
}
