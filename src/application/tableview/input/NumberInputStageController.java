package application.tableview.input;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import jiro.lib.javafx.scene.control.NumericTextField;

public class NumberInputStageController {
  private int firstValue;

  // **************************************************
  // コンポーネント
  // **************************************************
  @FXML private BorderPane root;
  private NumericTextField textField;
  @FXML private Button okButton;
  @FXML private Button cancelButton;

  @FXML
  private void initialize() {
    textField = new NumericTextField(0, 2000);
    BorderPane.setMargin(textField, new Insets(10.0));
    root.setCenter(textField);
  }

  // **************************************************
  // イベント
  // **************************************************
  @FXML
  private void rootOnKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      cancel();
    } else if (event.getCode() == KeyCode.ENTER) {
      ok();
    }
  }

  @FXML
  private void ok() {
    okButton.getScene().getWindow().hide();
  }

  @FXML
  void cancel() {
    textField.setText("" + firstValue);
    okButton.getScene().getWindow().hide();
  }

  // **************************************************
  // Getter / Setter
  // **************************************************
  int getValue() {
    return Integer.parseInt(textField.getText());
  }

  void setValue(int recordsCount) {
    firstValue = recordsCount;
    textField.setText("" + recordsCount);
  }
}
