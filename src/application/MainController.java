package application;

import java.io.File;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

import application.tableview.SkillTableViewBorderPane;
import application.tableview.SkillTableViewBorderPaneController;
import command.ICommand;
import command.UndoRedoManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import jiro.lib.javafx.stage.FileChooserManager;

public class MainController {
  private FileChooserManager fcm = new FileChooserManager("Text Files", "*.json");

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
  private TextArea noteTextArea;

  @FXML
  private void initialize() {
    skillTableView = new SkillTableViewBorderPane(this);
    skillTableViewController = skillTableView.getController();
    tableViewBorderPane.setCenter(skillTableView);
  }

  @FXML
  private void openFile() {
    Optional<File> fileOpt = fcm.openFile();
    fileOpt.ifPresent(f -> {
      skillTableViewController.setSkillDatas(f);
    });
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
}
