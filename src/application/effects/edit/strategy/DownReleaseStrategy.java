package application.effects.edit.strategy;

import javafx.scene.control.ComboBox;
import util.dictionary.Ability;

class DownReleaseStrategy extends EditStrategy {
  private ComboBox<String> comboBox;

  public DownReleaseStrategy() {
  }

  public DownReleaseStrategy(ComboBox<String> aComboBox) {
    comboBox = aComboBox;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Ability.values()[dataId].getText();
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    comboBox.getSelectionModel().select(dataId);
  }

  @Override
  double[] getValues() {
    double[] values = {
        34.0,
        comboBox.getSelectionModel().getSelectedIndex(),
        0.0,
        0.0,
    };
    return values;
  }

  @Override
  void changeDisable() {
    comboBox.setDisable(false);
  }
}
