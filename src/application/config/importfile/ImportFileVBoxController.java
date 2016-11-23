package application.config.importfile;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ImportFileVBoxController {
  public static final String DESCRIPTION = "インポートするファイルの場所を変更します。";

  @FXML private CheckBox autoImportCheckBox;

  @FXML private Label descriptionLabel;
  @FXML private RadioButton projectRadioButton;
  @FXML private TextField projectPathTextField;

  @FXML private RadioButton inputRadioButton;
  @FXML private TextField inputPathTextField;

  @FXML
  private void initialize() {
    ToggleGroup group = new ToggleGroup();
    projectRadioButton.setToggleGroup(group);
    inputRadioButton.setToggleGroup(group);
  }

  @FXML
  private void autoImportCheckBoxOnClicked() {
    boolean selected = autoImportCheckBox.isSelected();
    switchClickable(!selected);
    if (selected) {
      if (projectRadioButton.isSelected()) {
        projectRadioButtonOnClicked();
      } else if (inputRadioButton.isSelected()) {
        inputRadioButtonOnClicked();
      }
    }
  }

  /**
   * 各コンポーネントをクリック可能不可能を切り替える。
   * @param clickable true = 選択不可, false = 選択可能
   */
  private void switchClickable(boolean disable) {
    descriptionLabel.setDisable(disable);
    projectRadioButton.setDisable(disable);
    projectPathTextField.setDisable(disable);

    inputRadioButton.setDisable(disable);
    inputPathTextField.setDisable(disable);
  }

  @FXML
  private void projectRadioButtonOnClicked() {
    disableImportTextFields();
    projectPathTextField.setDisable(false);
  }

  @FXML
  private void inputRadioButtonOnClicked() {
    disableImportTextFields();
    inputPathTextField.setDisable(false);
  }

  private void disableImportTextFields() {
    projectPathTextField.setDisable(true);
    inputPathTextField.setDisable(true);
  }

  public String getProjectPath() {
    return projectPathTextField.getText();
  }

  public String getInputPath() {
    return inputPathTextField.getText();
  }

  public void setProjectPath(String projectPath) {
    projectPathTextField.setText(projectPath);
  }

  public void setInputPath(String inputPath) {
    inputPathTextField.setText(inputPath);
  }
}
