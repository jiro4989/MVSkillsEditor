package application.effects.edit.strategy;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    return Parameters.values()[dataId].name() + " ＋ " + (int) value1;
  }

  @Override
  String convertJsonString() {
    return null;
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    comboBox.getSelectionModel().select(dataId);
    textField.setText("" + (int) value1);
  }
}
