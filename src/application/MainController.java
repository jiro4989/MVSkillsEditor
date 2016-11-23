package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

import application.config.Config;
import application.config.ConfigStage;
import application.effects.EffectsTableViewBorderPane;
import application.effects.EffectsTableViewBorderPaneController;
import application.tableview.SkillTableViewBorderPane;
import application.tableview.SkillTableViewBorderPaneController;
import application.tableview.command.ICommand;
import application.tableview.command.UndoRedoManager;
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
  private DirectoryChooserManager projectDcm;
  private DirectoryChooserManager folderDcm;

  private UndoRedoManager undoRedoManager = new UndoRedoManager();
  private Stack<Integer> undoCountStack = new Stack<>();
  private Stack<Integer> redoCountStack = new Stack<>();

  private Config config = new Config();

  // **************************************************
  // メニューバー
  // **************************************************
  @FXML private MenuItem newMenuItem;
  @FXML private MenuItem importProjectMenuItem;
  @FXML private MenuItem importFolderMenuItem;
  @FXML private MenuItem saveMenuItem;
  @FXML private MenuItem saveAsMenuItem;
  @FXML private MenuItem configMenuItem;
  @FXML private MenuItem quitMenuItem;

  @FXML private MenuItem undoMenuItem;
  @FXML private MenuItem redoMenuItem;

  @FXML private MenuItem previousMenuItem;
  @FXML private MenuItem nextMenuItem;

  // **************************************************
  // テーブルビュー
  // **************************************************
  @FXML private BorderPane tableViewBorderPane;
  private SkillTableViewBorderPane skillTableView;
  private SkillTableViewBorderPaneController skillTableViewController;

  // **************************************************
  // ツールバー
  // **************************************************
  @FXML private Label xLabel;
  @FXML private Label yLabel;

  @FXML private TextField insertTextField;
  @FXML private Button applyButton;
  @FXML private MenuItem toolBarInsertMenuItem;
  @FXML private MenuItem toolBarHeadInsertMenuItem;
  @FXML private MenuItem toolBarFootInsertMenuItem;

  // **************************************************
  // プレビュー
  // **************************************************
  @FXML private TitledPane effectsTitledPane;
  private EffectsTableViewBorderPane effectsTableView;
  private EffectsTableViewBorderPaneController effectsTableViewController;

  @FXML private TextArea noteTextArea;

  @FXML
  private void initialize() {
    skillTableView = new SkillTableViewBorderPane(this);
    skillTableViewController = skillTableView.getController();
    tableViewBorderPane.setCenter(skillTableView);

    effectsTableView = new EffectsTableViewBorderPane(this);
    effectsTableViewController = effectsTableView.getController();
    effectsTitledPane.setContent(effectsTableView);

    projectDcm = new DirectoryChooserManager();
    folderDcm = new DirectoryChooserManager();
  }

  /**
   * RPGツクールMVのプロジェクトフォルダのルートを選択することで、
   * ルートから辿って必要なファイルを取得する。
   */
  @FXML
  private void importProject() {
    Stage stage = (Stage) effectsTitledPane.getScene().getWindow();
    Optional<File> dirOpt = projectDcm.openDirectory(stage);
    dirOpt.ifPresent(rootDirectory -> {
      String rootPath = rootDirectory.getAbsolutePath();
      String dataPath = rootPath + File.separator + "data";
      boolean success = successSetData(dataPath);

      if (success) {
        config.projectPath = rootPath;
      } else {
        String content = "プロジェクトフォルダを間違えていないか" + System.getProperty("line.separator")
            + "dataフォルダやファイルが存在しているか確認してください。";
        showAlert("ファイルが見つかりません。", content);
      }
    });
  }

  /**
   * 必要なファイルを一つのフォルダにまとめておき、
   * そのフォルダを選択することで必要なファイルを取得する。
   */
  @FXML
  private void importFolder() {
    Stage stage = (Stage) effectsTitledPane.getScene().getWindow();
    Optional<File> dirOpt = folderDcm.openDirectory(stage);
    dirOpt.ifPresent(inputFolder -> {
      String inputPath = inputFolder.getAbsolutePath();
      boolean success = successSetData(inputPath);

      if (success) {
        config.inputPath = inputPath;
      } else {
        showAlert("ファイルが見つかりません。", "選択したフォルダ内に必要なファイルが存在するか確認してください。");
      }
    });
  }

  /**
   * 渡したファイルパス文字列からSkillDataファイルを取得し、テーブルに追加する。
   * この時、チェック対象の全てのファイルが存在していた場合は、trueを返し、
   * 一つでも取得対象のファイルが欠けていた場合は、falseを返す。
   * @param path ファイルの場所。
   * @return 成功 or 失敗
   */
  private boolean successSetData(String path) {
    final String SEP = File.separator;
    File skillFile = new File(path + SEP + "Skills.json");
    File animationFile = new File(path + SEP + "Animations.json");
    File stateFile = new File(path + SEP + "States.json");
    File commonEventFile = new File(path + SEP + "CommonEvents.json");
    File systemFile = new File(path + SEP + "System.json");

    String rootPath = new File(path).getParent();
    File iconSetImage1 = new File(rootPath + SEP + "img" + SEP + "system" + SEP + "IconSet.png");
    File iconSetImage2 = new File(path + SEP + "IconSet.png");
    if (skillFile.exists()
        && animationFile.exists()
        && stateFile.exists()
        && commonEventFile.exists()
        && systemFile.exists()
        && (iconSetImage1.exists() || iconSetImage2.exists())) {

      skillTableViewController.setSkillDatas(skillFile, systemFile);
      File iconFile = iconSetImage1.exists() ? iconSetImage1 : iconSetImage2;
      skillTableViewController.setIconFile(iconFile);
      effectsTableViewController.setStateList(stateFile,
          skillTableViewController.getNormalAttackText());
      effectsTableViewController.setCommonEventList(commonEventFile);
      return true;
    }
    return false;
  }

  /**
   * プレビュー画面の選択可能状態を変更する。
   * @param disable
   */
  public void changeDisablePreviews(boolean disable) {
    if (noteTextArea.isDisable()) {
      effectsTableViewController.setDisable(disable);
      noteTextArea.setDisable(disable);
    }
  }

  /**
   * 警告ウィンドウを表示する。
   * @param header ヘッダーテキスト
   * @param content メインテキスト
   */
  private void showAlert(String header, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private void openConfigStage() {
    ConfigStage cs = new ConfigStage(config);
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
  private void previousMenuItemOnClicked() {
    skillTableViewController.movePrevious();
  }

  @FXML
  private void nextMenuItemOnClicked() {
    skillTableViewController.moveNext();
  }

  @FXML
  private void insertText() {
    skillTableViewController.insertText(insertTextField.getText());
  }

  @FXML
  private void noteTextAreaOnKeyReleased() {
    skillTableViewController.setNote(noteTextArea.getText());
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

  public void updateEffectsTableView(String effectsText, ArrayList<String> skillsList) {
    effectsTableViewController.update(effectsText, skillsList);
  }

  public void closeAction() {
    skillTableViewController.outputPropertiesFile();
  }

  public void setNoteText(String text) {
    noteTextArea.setText(text);
  }

  public String getSelectedEffects() {
    return skillTableViewController.getSelectedEffects();
  }

  /**
   * effectsセルの値を更新する。
   * @param selectedIndex
   * @param values
   */
  public void updateEffectsCell(int selectedIndex, double[] values) {
    skillTableViewController.updateEffectsCell(selectedIndex, values);
  }
}
