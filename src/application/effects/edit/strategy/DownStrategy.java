package application.effects.edit.strategy;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

class DownStrategy extends EditStrategy {
  private ComboBox<String> downComboBox;
  private TextField downTextField;

  public DownStrategy() {
  }

  public DownStrategy(ComboBox<String> downComboBox, TextField downTextField) {
    this.downComboBox = downComboBox;
    this.downTextField = downTextField;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return Parameters.values()[dataId].name() + " " + (int) value1 + " ターン";
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    downComboBox.getSelectionModel().select(dataId);
    downTextField.setText("" + (int) value1);
  }

  @Override
  double[] getValues() {
    double[] values = {
        32.0,
        downComboBox.getSelectionModel().getSelectedIndex(),
        Double.parseDouble(downTextField.getText()),
        0.0,
    };
    return values;
  }
}
