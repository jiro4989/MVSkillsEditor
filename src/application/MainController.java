package application;

import java.io.File;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

import application.config.ConfigStage;
import application.effects.EffectsTableViewBorderPane;
import application.effects.EffectsTableViewBorderPaneController;
import application.tableview.SkillTableViewBorderPane;
import application.tableview.SkillTableViewBorderPaneController;
import command.ICommand;
import command.UndoRedoManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jiro.lib.javafx.stage.DirectoryChooserManager;

public class MainController {
  // private FileChooserManager fcm = new
  // FileChooserManager("Text Files", "*.json");
  private DirectoryChooserManager dcm;

  private UndoRedoManager undoRedoManager = new UndoRedoManager();
  private Stack<Integer> undoCountStack = new Stack<>();
  private Stack<Integer> redoCountStack = new Stack<>();

  // **************************************************
  // メニューバー
  // **************************************************
  @FXML
  private MenuItem newMenuItem;
  @FXML
  private MenuItem openMenuItem;
  @FXML
  private MenuItem saveMenuItem;
  @FXML
  private MenuItem saveAsMenuItem;
  @FXML
  private MenuItem configMenuItem;
  @FXML
  private MenuItem quitMenuItem;

  @FXML
  private MenuItem undoMenuItem;
  @FXML
  private MenuItem redoMenuItem;

  // **************************************************
  // テーブルビュー
  // **************************************************
  @FXML
  private BorderPane tableViewBorderPane;
  private SkillTableViewBorderPane skillTableView;
  private SkillTableViewBorderPaneController skillTableViewController;

  // **************************************************
  // ツールバー
  // **************************************************
  @FXML
  private Label xLabel;
  @FXML
  private Label yLabel;

  @FXML
  private TextField insertTextField;
  @FXML
  private Button applyButton;
  @FXML
  private MenuItem toolBarInsertMenuItem;
  @FXML
  private MenuItem toolBarHeadInsertMenuItem;
  @FXML
  private MenuItem toolBarFootInsertMenuItem;

  // **************************************************
  // プレビュー
  // **************************************************
  @FXML
  private TitledPane effectsTitledPane;
  private EffectsTableViewBorderPane effectsTableView;
  private EffectsTableViewBorderPaneController effectsTableViewController;

  @FXML
  private TextArea noteTextArea;

  @FXML
  private void initialize() {
    skillTableView = new SkillTableViewBorderPane(this);
    skillTableViewController = skillTableView.getController();
    tableViewBorderPane.setCenter(skillTableView);

    effectsTableView = new EffectsTableViewBorderPane(this);
    effectsTableViewController = effectsTableView.getController();
    effectsTitledPane.setContent(effectsTableView);

    dcm = new DirectoryChooserManager();
  }

  @FXML
  private void openFile() {
    Stage stage = (Stage) effectsTitledPane.getScene().getWindow();
    Optional<File> dirOpt = dcm.openDirectory(stage);
    dirOpt.ifPresent(rootDirectory -> {
      final String SEP = File.separator;
      String rootPath = rootDirectory.getAbsolutePath();
      String dataPath = rootPath + SEP + "data";
      File skillData = new File(dataPath + SEP + "Skills.json");
      File animationData = new File(dataPath + SEP + "Skills.json");
      File stateData = new File(dataPath + SEP + "Skills.json");
      File commonEventData = new File(dataPath + SEP + "Skills.json");
      if (skillData.exists()
          && animationData.exists()
          && stateData.exists()
          && commonEventData.exists()) {
        skillTableViewController.setSkillDatas(skillData);
        return;
      }
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("ファイルが見つかりません。");
      alert.setContentText("プロジェクトフォルダを間違えていないか" + System.getProperty("line.separator")
          + "dataフォルダやファイルが存在しているか確認してください。");
      alert.showAndWait();
    });
  }

  @FXML
  private void openConfigStage() {
    ConfigStage cs = new ConfigStage();
    cs.showAndWait();
  }

  @FXML
  private void undo() {
    if (!undoCountStack.isEmpty()) {
      int invokeCount = undoCountStack.pop();
      IntStream.range(0, invokeCount).forEach(i -> {
        undoRedoManager.undo();
      });
      redoCountStack.push(invokeCount);
    }
  }

  @FXML
  private void redo() {
    if (!redoCountStack.isEmpty()) {
      int invokeCount = redoCountStack.pop();
      IntStream.range(0, invokeCount).forEach(i -> {
        undoRedoManager.redo();
      });
      undoCountStack.push(invokeCount);
    }
  }

  @FXML
  private void insertText() {
    skillTableViewController.insertText(insertTextField.getText());
  }

  /**
   * コマンドを実行する。
   * @param command
   *          コマンド
   */
  public void invoke(ICommand command) {
    undoRedoManager.invoke(command);
  }

  /**
   * コマンドの繰り返し回数をプッシュする。
   * @param invokeCount
   *          繰り返し回数
   */
  public void pushUndoCount(int invokeCount) {
    undoCountStack.push(invokeCount);
  }

  /**
   * 座標ラベルを更新する。
   * @param x
   *          カラムインデックス
   * @param y
   *          ロウインデックス
   */
  public void updateAxisLabels(int x, int y) {
    xLabel.setText("" + x);
    yLabel.setText("" + y);
  }

  public void setNoteText(String text) {
    noteTextArea.setText(text);
  }

  public void closeAction() {
    skillTableViewController.outputPropertiesFile();
  }

  public void updateEffectsTableView(String effectsText) {
    effectsTableViewController.update(effectsText);
  }
}
