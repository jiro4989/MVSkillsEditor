package application.config;

import java.io.IOException;

import application.config.import_file.ImportFileVBoxController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ConfigStageController {
  private VBox importFileVBox;
  private ImportFileVBoxController importFileVBoxController;
  private Config config;

  @FXML
  private TreeView<String> treeView;
  @FXML
  private BorderPane borderPane;
  @FXML
  private Label headerLabel;
  @FXML
  private Label descriptionLabel;
  @FXML
  private Button okButton;
  @FXML
  private Button cancelButton;

  @FXML
  private void initialize() {
    TreeItem<String> rootItem = new TreeItem<>("設定項目");
    rootItem.setExpanded(true);
    TreeItem<String> importFileItem = new TreeItem<>("インポートファイル");
    rootItem.getChildren().add(importFileItem);
    treeView.setRoot(rootItem);
    treeView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldVal, newVal) -> updateConfigScreen(newVal));

    FXMLLoader importFileVBoxLoader = new FXMLLoader(
        getClass().getResource("/application/config/import_file/ImportFileVBox.fxml"));
    try {
      importFileVBox = (VBox) importFileVBoxLoader.load();
      importFileVBoxController = importFileVBoxLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 設定画面を更新する。
   * 選択したTreeItemの文字列によって表示する画面を切り替える。
   * @param selectedItem 選択したTreeItem
   */
  private void updateConfigScreen(TreeItem<String> selectedItem) {
    final String headerText = selectedItem.getValue();
    if ("インポートファイル".equals(headerText)) {
      borderPane.setCenter(importFileVBox);
      headerLabel.setText(headerText);
      descriptionLabel.setText(ImportFileVBoxController.DESCRIPTION);
      importFileVBoxController.setProjectPath(config.projectPath);
      importFileVBoxController.setInputPath(config.inputPath);
    } else {
    }
  }

  @FXML
  private void okButtonOnClicked() {
    config.projectPath = importFileVBoxController.getProjectPath();
    config.inputPath = importFileVBoxController.getInputPath();
    okButton.getScene().getWindow().hide();
  }

  @FXML
  private void cancelButtonOnClicked() {
    cancelButton.getScene().getWindow().hide();
  }

  public void setConfig(Config config) {
    this.config = config;
  }
}
