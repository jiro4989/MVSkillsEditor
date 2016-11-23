package application.effects.edit.strategy;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.dictionary.Ability;

class GrowthStrategy extends EditStrategy {
  private ComboBox<String> comboBox;
  private TextField textField;

  public GrowthStrategy() {
  }

  public GrowthStrategy(ComboBox<String> aComboBox, TextField aTextField) {
    comboBox = aComboBox;
    textField = aTextField;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Ability.values()[dataId].getText() + " ＋ " + (int) value1;
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    comboBox.getSelectionModel().select(dataId);
    textField.setText("" + (int) value1);
  }

  @Override
  double[] getValues() {
    double[] values = {
        42.0,
        comboBox.getSelectionModel().getSelectedIndex(),
        Double.parseDouble(textField.getText()),
        0.0,
    };
    return values;
  }

  @Override
  void changeDisable() {
    comboBox.setDisable(false);
    textField.setDisable(false);
  }
}
