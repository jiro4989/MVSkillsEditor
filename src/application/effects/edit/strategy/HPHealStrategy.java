package application.effects.edit.strategy;

import javafx.scene.control.TextField;

class HPHealStrategy extends EditStrategy {
  private TextField textField1;
  private TextField textField2;

  public HPHealStrategy() {
  }

  public HPHealStrategy(TextField textField1, TextField textField2) {
    this.textField1 = textField1;
    this.textField2 = textField2;
  }

  @Override
  String formatToContentText(int codeId, int dataId, double value1, double value2) {
    if ((int) value2 == 0) {
      return (int) (value1 * 100) + " %";
    }
    return (int) (value1 * 100) + " %" + " ＋ " + (int) value2;
  }

  @Override
  double[] getValues() {
    double[] values = {
        11.0,
        0.0,
        Double.parseDouble(textField1.getText()) / 100,
        Double.parseDouble(textField2.getText()),
    };
    return values;
  }

  @Override
  void setValue(int dataId, double value1, double value2) {
    textField1.setText("" + (int) (value1 * 100));
    textField2.setText("" + (int) value2);
  }
}
