package application.config.import_file;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import jiro.lib.javafx.stage.DirectoryChooserManager;

public class ImportFileVBoxController {
  public static final String DESCRIPTION = "インポートするファイルの場所を変更します。";

  @FXML
  private CheckBox autoImportCheckBox;

  @FXML
  private Label descriptionLabel;
  @FXML
  private RadioButton projectRadioButton;
  @FXML
  private TextField projectPathTextField;
  @FXML
  private Button openProjectDirButton;

  @FXML
  private RadioButton inputRadioButton;
  @FXML
  private TextField inputPathTextField;
  @FXML
  private Button openInputDirButton;

  @FXML
  private void initialize() {
    ToggleGroup group = new ToggleGroup();
    projectRadioButton.setToggleGroup(group);
    inputRadioButton.setToggleGroup(group);
  }

  @FXML
  private void autoImportCheckBoxOnClicked() {
    switchClickable(!autoImportCheckBox.isSelected());
  }

  /**
   * 各コンポーネントをクリック可能不可能を切り替える。
   * @param clickable true = 選択不可, false = 選択可能
   */
  private void switchClickable(boolean disable) {
    descriptionLabel.setDisable(disable);
    projectRadioButton.setDisable(disable);
    projectPathTextField.setDisable(disable);
    openProjectDirButton.setDisable(disable);

    inputRadioButton.setDisable(disable);
    inputPathTextField.setDisable(disable);
    openInputDirButton.setDisable(disable);
  }

  @FXML
  private void openProjectDirButtonOnClicked() {
    selectDirAndSetText(projectPathTextField);
  }

  @FXML
  private void openInputDirButtonOnClicked() {
    selectDirAndSetText(inputPathTextField);
  }

  private void selectDirAndSetText(TextField textField) {
    String dirPath = textField.getText();
    File directory = new File(dirPath);
    if (directory.exists()) {
      dirPath = new File(dirPath).getParent();
      Stage stage = (Stage) descriptionLabel.getScene().getWindow();
      DirectoryChooserManager dcm = new DirectoryChooserManager(dirPath);
      Optional<File> dirOpt = dcm.openDirectory(stage);
      dirOpt.ifPresent(dir -> {
        textField.setText(dir.getAbsolutePath());
      });
    }
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
