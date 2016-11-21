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
  void setValue(int dataId, double value1, double value2) {
    stateListView.getSelectionModel().select(dataId);
    releaseStateTextField.setText("" + (int) (value1 * 100));
  }

  @Override
  double[] getValues() {
    double[] values = {
        22.0,
        stateListView.getSelectionModel().getSelectedIndex(),
        Double.parseDouble(releaseStateTextField.getText()) / 100,
        0.0,
    };
    return values;
  }

  @Override
  void changeDisable() {
    int currentIndex = stateListView.getSelectionModel().isEmpty()
        ? 0 : stateListView.getSelectionModel().getSelectedIndex();
    stateListView.getSelectionModel().select(currentIndex);
    stateListView.setDisable(false);
    releaseStateTextField.setDisable(false);
  }
}
