package application.config.io;

import application.MainController;
import application.config.Config;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class IOVBoxController {
  public static final String DESCRIPTION = "ファイル入出力の設定を変更します。";
  private Config config;

  @FXML private CheckBox autoImportCheckBox;

  @FXML private Label descriptionLabel;
  @FXML private RadioButton projectRadioButton;
  @FXML private TextField projectPathTextField;

  @FXML private RadioButton inputRadioButton;
  @FXML private TextField inputPathTextField;

  @FXML private CheckBox backupCheckBox;
  @FXML private BorderPane backupBorderPane;
  @FXML private TextField backupTextField;

  @FXML
  private void initialize() {
    config = MainController.getConfig();

    ToggleGroup group = new ToggleGroup();
    projectRadioButton.setToggleGroup(group);
    inputRadioButton.setToggleGroup(group);

    autoImportCheckBox.setSelected(config.autoInput);
    projectRadioButton.setSelected(config.projectIsSelected);
    projectPathTextField.setText(config.projectPath);
    inputRadioButton.setSelected(config.inputIsSelected);
    backupCheckBox.setSelected(config.autoBackup);

    projectPathTextField.setDisable(!projectRadioButton.isSelected());
    inputPathTextField.setDisable(!inputRadioButton.isSelected());
  }

  @FXML
  private void autoImportCheckBoxOnClicked() {
    switchClickable();
  }

  /**
   * 各コンポーネントをクリック可能不可能を切り替える。
   * @param clickable true = 選択不可, false = 選択可能
   */
  public void switchClickable() {
    boolean disable = !autoImportCheckBox.isSelected();

    descriptionLabel.setDisable(disable);
    projectRadioButton.setDisable(disable);
    projectPathTextField.setDisable(disable);

    inputRadioButton.setDisable(disable);
    inputPathTextField.setDisable(disable);

    if (!disable) {
      projectPathTextField.setDisable(!projectRadioButton.isSelected());
      inputPathTextField.setDisable(!inputRadioButton.isSelected());
    }
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

  @FXML
  public void disableBackup() {
    boolean disable = !backupCheckBox.isSelected();
    backupBorderPane.getChildren().forEach(node -> node.setDisable(disable));
  }

  public String getProjectPath() {
    return projectPathTextField.getText();
  }

  public String getInputPath() {
    return inputPathTextField.getText();
  }

  public boolean getAutoInput() {
    return autoImportCheckBox.isSelected();
  }

  public boolean getSelectedOfProject() {
    return projectRadioButton.isSelected();
  }

  public boolean getSelectedOfInput() {
    return inputRadioButton.isSelected();
  }

  public boolean getAutoBackup() {
    return backupCheckBox.isSelected();
  }

  public void setProjectPath(String projectPath) {
    projectPathTextField.setText(projectPath);
  }

  public void setInputPath(String inputPath) {
    inputPathTextField.setText(inputPath);
  }
}
