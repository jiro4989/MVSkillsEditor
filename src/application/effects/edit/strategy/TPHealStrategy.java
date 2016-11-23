package application.effects.edit.strategy;

import javafx.scene.control.TextField;

class TPHealStrategy extends EditStrategy {
  private TextField textField;

  public TPHealStrategy() {
  }

  public TPHealStrategy(TextField textField) {
    this.textField = textField;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    return "" + (int) value1;
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    textField.setText("" + (int) value1);
  }

  @Override
  double[] getValues() {
    double[] values = {
        13.0,
        0.0,
        Double.parseDouble(textField.getText()),
        0.0
    };
    return values;
  }

  @Override
  void changeDisable() {
    textField.setDisable(false);
  }
}
