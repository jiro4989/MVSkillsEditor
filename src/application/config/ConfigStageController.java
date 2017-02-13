package application.config;

import java.io.IOException;

import application.MainController;
import application.config.io.IOVBoxController;
import application.config.tableview.TableViewVBoxController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ConfigStageController {
  private Config config;

  // **************************************************
  // 別パッケージで定義したコンポーネント
  // **************************************************
  private VBox ioVBox;
  private IOVBoxController ioVBoxController;
  private VBox tableViewVBox;
  private TableViewVBoxController tableViewVBoxController;

  // **************************************************
  // このクラスのコンポーネント
  // **************************************************
  @FXML private BorderPane root;
  @FXML private TreeView<String> treeView;
  @FXML private BorderPane borderPane;
  @FXML private Label headerLabel;
  @FXML private Label descriptionLabel;
  @FXML private Button okButton;
  @FXML private Button cancelButton;

  @FXML
  private void initialize() {
    config = MainController.getConfig();

    TreeItem<String> rootItem = new TreeItem<>("設定項目");
    rootItem.setExpanded(true);
    TreeItem<String> importFileItem = new TreeItem<>("インプット・アウトプット");
    TreeItem<String> tableItem = new TreeItem<>("テーブル");
    rootItem.getChildren().add(importFileItem);
    rootItem.getChildren().add(tableItem);
    treeView.setRoot(rootItem);

    treeView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldVal, newVal) -> updateConfigScreen(newVal));

    FXMLLoader ioVBoxLoader = new FXMLLoader(
        getClass().getResource("/application/config/io/IOVBox.fxml"));
    FXMLLoader tableViewVBoxLoader = new FXMLLoader(
        getClass().getResource("/application/config/tableview/TableViewVBox.fxml"));
    try {
      ioVBox = (VBox) ioVBoxLoader.load();
      ioVBoxController = ioVBoxLoader.getController();

      tableViewVBox = (VBox) tableViewVBoxLoader.load();
      tableViewVBoxController = tableViewVBoxLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void changeOptions() {
    treeView.getSelectionModel().select(1);
    ioVBoxController.switchClickable();
    ioVBoxController.disableBackup();
  }

  // **************************************************
  // イベント
  // **************************************************
  /**
   * 設定画面を更新する。
   * 選択したTreeItemの文字列によって表示する画面を切り替える。
   * @param selectedItem 選択したTreeItem
   */
  private void updateConfigScreen(TreeItem<String> selectedItem) {
    final String headerText = selectedItem.getValue();
    if ("インプット・アウトプット".equals(headerText)) {
      borderPane.setCenter(ioVBox);
      headerLabel.setText(headerText);
      descriptionLabel.setText(IOVBoxController.DESCRIPTION);

      ioVBoxController.setProjectPath(config.projectPath);
    } else if ("テーブル".equals(headerText)) {
      borderPane.setCenter(tableViewVBox);
      headerLabel.setText(headerText);
      descriptionLabel.setText(TableViewVBoxController.DESCRIPTION);
    }
  }

  @FXML
  private void rootOnKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      cancelButtonOnClicked();
    }
  }

  @FXML
  private void okButtonOnClicked() {
    config.autoInput = ioVBoxController.getAutoInput();
    config.projectIsSelected = ioVBoxController.getSelectedOfProject();
    config.projectPath = ioVBoxController.getProjectPath();
    config.inputIsSelected = ioVBoxController.getSelectedOfInput();
    config.autoBackup = ioVBoxController.getAutoBackup();
    config.tableViewFontSize = tableViewVBoxController.getFontSize();
    config.tableCellHeight = tableViewVBoxController.getCellHeight();
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
