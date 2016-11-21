package application.effects.edit.strategy;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

class UpStrategy extends EditStrategy {
  private ComboBox<String> upComboBox;
  private TextField upTextField;

  public UpStrategy() {
  }

  public UpStrategy(ComboBox<String> upComboBox, TextField upTextField) {
    this.upComboBox = upComboBox;
    this.upTextField = upTextField;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Parameters.values()[dataId].name() + " " + (int) value1 + " ターン";
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    upComboBox.getSelectionModel().select(dataId);
    upTextField.setText("" + (int) value1);
  }

  @Override
  double[] getValues() {
    double[] values = {
        31.0,
        upComboBox.getSelectionModel().getSelectedIndex(),
        Double.parseDouble(upTextField.getText()),
        0.0,
    };
    return values;
  }
}
