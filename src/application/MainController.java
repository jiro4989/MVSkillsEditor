package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.config.Config;
import application.config.ConfigStage;
import application.effects.EffectsTableViewBorderPane;
import application.effects.EffectsTableViewBorderPaneController;
import application.tableview.SkillTableViewBorderPane;
import application.tableview.SkillTableViewBorderPaneController;
import application.tableview.command.ICommand;
import application.tableview.command.UndoRedoManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jiro.lib.javafx.stage.DirectoryChooserManager;
import util.json.JsonSkill;

public class MainController {
  private DirectoryChooserManager projectDcm;

  private UndoRedoManager undoRedoManager = new UndoRedoManager();
  private LinkedList<Integer> undoCountStack = new LinkedList<>();
  private LinkedList<Integer> redoCountStack = new LinkedList<>();

  private static Config config = new Config();
  private Properties favoriteProp;

  // **************************************************
  // メニューバー
  // **************************************************
  @FXML private MenuItem importProjectMenuItem;
  @FXML private MenuItem importFolderMenuItem;
  @FXML private MenuItem saveMenuItem;
  @FXML private MenuItem configMenuItem;
  @FXML private MenuItem quitMenuItem;

  @FXML private MenuItem undoMenuItem;
  @FXML private MenuItem redoMenuItem;
  @FXML private MenuItem changeMaxRecordsMenuItem;

  @FXML private MenuItem previousMenuItem;
  @FXML private MenuItem nextMenuItem;

  // **************************************************
  // ツールバー(上)
  // **************************************************
  @FXML private ToolBar topToolBar;
  @FXML private Button projectButton;
  @FXML private Button folderButton;
  @FXML private Button undoButton;
  @FXML private Button redoButton;
  @FXML private Button configButton;
  @FXML private ComboBox<String> insertComboBox;

  // **************************************************
  // Root
  // **************************************************
  @FXML private SplitPane rootSplitPane;

  // **************************************************
  // テーブルビュー
  // **************************************************
  @FXML private BorderPane tableViewBorderPane;
  private SkillTableViewBorderPane skillTableView;
  private SkillTableViewBorderPaneController skillTableViewController;

  // **************************************************
  // ツールバー(下)
  // **************************************************
  @FXML private Label xLabel;
  @FXML private Label yLabel;

  @FXML private TextField insertTextField;
  @FXML private Button applyButton;
  @FXML private ComboBox<String> applyComboBox;

  @FXML private ComboBox<String> favoriteComboBox;
  @FXML private Button clearButton;

  // **************************************************
  // プレビュー
  // **************************************************
  @FXML private SplitPane previewSplitPane;
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

    applyComboBox.getSelectionModel().select(0);

    favoriteComboBox.setItems(FXCollections.observableArrayList());

    // **************************************************
    // プロパティを適用
    // **************************************************
    favoriteProp = new Properties();
    importFavoriteItems();

    skillTableViewController.setTableViewFontSize(config.tableViewFontSize);
    skillTableViewController.setTableCellSize(config.tableCellHeight);
  }

  private static final File FAVORITE_FILE = new File("./properties/favoriteItems.xml");

  private void importFavoriteItems() {
    if (!FAVORITE_FILE.exists()) {
      return;
    }

    try {
      InputStream in = new FileInputStream(FAVORITE_FILE);
      favoriteProp.loadFromXML(in);
      for (int i = 0; i < 20; i++) {
        String item = favoriteProp.getProperty("item" + i);
        if (item == null || item.equals("NULL")) {
          break;
        }
        favoriteComboBox.getItems().add(item);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (InvalidPropertiesFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void exportFavoriteItems() {
    try (OutputStream out = new FileOutputStream(FAVORITE_FILE)) {
      int size = favoriteComboBox.getItems().size();
      for (int i = 0; i < 20; i++) {
        if (i < size) {
          favoriteProp.setProperty("item" + i, favoriteComboBox.getItems().get(i));
        } else {
          favoriteProp.setProperty("item" + i, "NULL");
        }
      }
      favoriteProp.storeToXML(out, "お気に入りに追加したアイテム");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void setDividers() {
    rootSplitPane.setDividerPosition(0, config.rootDivider);
    previewSplitPane.setDividerPosition(0, config.previewDivider);
    SplitPane tableSplit = skillTableViewController.getSplitPane();
    tableSplit.setDividerPosition(0, config.tableViewDivider);
  }

  /**
   * RPGツクールMVのプロジェクトフォルダのルートを選択することで、
   * ルートから辿って必要なファイルを取得する。
   */
  @FXML
  private void importProject() {
    Stage stage = (Stage) effectsTitledPane.getScene().getWindow();
    Optional<File> dirOpt = projectDcm.openDirectory(stage);
    dirOpt.ifPresent(dir -> {
      importProject(dir);
    });
  }

  private void importProject(File dir) {
    String rootPath = dir.getAbsolutePath();
    final String SEP = File.separator;
    String dataPath = rootPath + SEP + "data";
    boolean success = successSetData(dataPath);

    if (success) {
      config.projectPath = rootPath;
      config.projectIsSelected = true;
      config.inputIsSelected = false;
    } else {
      String content = "プロジェクトフォルダを間違えていないか" + System.getProperty("line.separator")
          + "dataフォルダやファイルが存在しているか確認してください。";
      showAlert("ファイルが見つかりません。", content);
    }
  }

  /**
   * 必要なファイルを一つのフォルダにまとめておき、
   * そのフォルダを選択することで必要なファイルを取得する。
   */
  @FXML
  private void importFolder() {
    boolean success = successSetData("./input");

    if (success) {
      config.projectIsSelected = false;
      config.inputIsSelected = true;
    } else {
      showAlert("ファイルが見つかりません。", "選択したフォルダ内に必要なファイルが存在するか確認してください。");
    }
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

      undoRedoManager.clear();
      undoCountStack.clear();
      redoCountStack.clear();
      undoButton.setDisable(true);
      redoButton.setDisable(true);
      insertComboBox.getItems().clear();

      skillTableViewController.setSkillDatas(skillFile, systemFile, animationFile);
      File iconFile = iconSetImage1.exists() ? iconSetImage1 : iconSetImage2;
      skillTableViewController.setIconFile(iconFile);
      effectsTableViewController.setStateList(stateFile,
          skillTableViewController.getNormalAttackText());
      effectsTableViewController.setCommonEventList(commonEventFile);

      Stage stage = (Stage)xLabel.getScene().getWindow();
      stage.setTitle(skillFile.getPath() + " - " + Main.TITLE);
      return true;
    }
    return false;
  }

  @FXML
  private void quitMenuItemOnAction() {
    closeAction();
    Platform.exit();
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
    skillTableViewController.setTableViewFontSize(config.tableViewFontSize);
    skillTableViewController.setTableCellSize(config.tableCellHeight);
  }

  @FXML
  private void saveMenuItemOnAction() {
    List<JsonSkill> skillList = skillTableViewController.makeSkillData();
    File file = new File("Skills.json");
    try (FileOutputStream fos = new FileOutputStream(file)) {
      File json = new File("Skills.json");
      json.createNewFile();

      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(new OutputStreamWriter(fos, "UTF-8"), skillList);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  @FXML
  private void undo() {
    if (!undoCountStack.isEmpty()) {
      int invokeCount = undoCountStack.pop();
      IntStream.range(0, invokeCount).forEach(i -> {
        undoRedoManager.undo();
      });
      redoCountStack.push(invokeCount);
      changeDisableUndoRedoButton();
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
      changeDisableUndoRedoButton();
    }
  }

  @FXML
  private void changeMaxRecords() {
    skillTableViewController.changeMaxRecords();
  }

  @FXML
  private void previousMenuItemOnClicked() {
    skillTableViewController.movePrevious();
  }

  @FXML
  private void nextMenuItemOnClicked() {
    skillTableViewController.moveNext();
  }

  // **************************************************
  // ツールバー(下)のイベント
  // **************************************************
  @FXML
  private void insertTextFieldOnKeyReleased(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
      insertSwitch();
    }
  }

  @FXML
  private void insertComboBoxOnHidden() {
    skillTableViewController.insertText(insertComboBox.getValue());
  }

  @FXML
  private void applyButtonOnAction() {
    insertSwitch();
  }

  @FXML
  private void applyComboBoxOnHidden() {
    insertSwitch();
  }

  @FXML
  private void applyComboBoxOnKeyReleased(KeyEvent event) {
    if (event.getCode() == KeyCode.SPACE) {
      insertSwitch();
    }
  }

  @FXML
  private void favoriteComboBoxOnKeyReleased(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
      String text = favoriteComboBox.getValue();
      if (!text.equals("お気に入り") && !text.equals("")) {
        favoriteComboBox.getItems().add(text);
        favoriteComboBox.setValue("");
      }
    }
  }

  @FXML
  private void favoriteComboBoxOnHidden() {
    String text = favoriteComboBox.getValue();
    insertTextField.setText(text);
    favoriteComboBox.setValue("");
  }

  @FXML
  private void clearButtonOnAction() {
    favoriteComboBox.setItems(FXCollections.observableArrayList());
  }

  private void insertSwitch() {
    if (skillTableViewController.isSelected()) {
      int selectedIndex = applyComboBox.getSelectionModel().getSelectedIndex();
      if (selectedIndex == 0) {
        normalInsert();
      } else if (selectedIndex == 1) {
        topInsert();
      } else if (selectedIndex == 2) {
        endInsert();
      }
    }
  }

  public void normalInsert() {
    insertText();
  }

  public void topInsert() {
    String text = insertTextField.getText();
    topInsert(text);
  }

  public void topInsert(String text) {
    if (text != null) {
      List<String> values = skillTableViewController.getselectedCellValues();
      List<Integer> rowIndices = skillTableViewController.getSelectedRowIndices();
      AtomicInteger index = new AtomicInteger(0);
      StringBuilder sb = new StringBuilder();
      rowIndices.stream().forEach(rowIndex -> {
        sb.append(text);
        sb.append(values.get(index.getAndIncrement()));
        skillTableViewController.invoke(sb.toString(), rowIndex);
        sb.setLength(0);
      });
      pushUndoCount(rowIndices.size());
    }
  }

  public void endInsert() {
    String text = insertTextField.getText();
    endInsert(text);
  }

  public void endInsert(String text) {
    if (text != null) {
      List<String> values = skillTableViewController.getselectedCellValues();
      List<Integer> rowIndices = skillTableViewController.getSelectedRowIndices();
      AtomicInteger index = new AtomicInteger(0);
      StringBuilder sb = new StringBuilder();
      rowIndices.stream().forEach(rowIndex -> {
        sb.append(values.get(index.getAndIncrement()));
        sb.append(text);
        skillTableViewController.invoke(sb.toString(), rowIndex);
        sb.setLength(0);
      });
      pushUndoCount(rowIndices.size());
    }
  }

  @FXML
  private void insertText() {
    String text = insertTextField.getText();
    if (text != null) {
      skillTableViewController.insertText(text);
    }
  }

  public void insertText(String text) {
    if (text != null) {
      skillTableViewController.insertText(text);
    }
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
    if (0 < invokeCount) {
      redoCountStack.clear();
      undoCountStack.push(invokeCount);
    }
    changeDisableUndoRedoButton();
  }

  private void changeDisableUndoRedoButton() {
    boolean disable = undoCountStack.isEmpty();
    undoButton.setDisable(disable);
    undoMenuItem.setDisable(disable);
    disable = redoCountStack.isEmpty();
    redoButton.setDisable(disable);
    redoMenuItem.setDisable(disable);
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
    skillTableViewController.exportPropertiesFile();
    exportFavoriteItems();

    config.rootX = xLabel.getScene().getWindow().getX();
    config.rootY = xLabel.getScene().getWindow().getY();
    config.rootWidth = xLabel.getScene().getWindow().getWidth();
    config.rootHeight = xLabel.getScene().getWindow().getHeight();
    config.rootDivider = rootSplitPane.getDividerPositions()[0];
    config.previewDivider = previewSplitPane.getDividerPositions()[0];
    SplitPane tableSplit = skillTableViewController.getSplitPane();
    config.tableViewDivider = tableSplit.getDividerPositions()[0];

    config.write();
  }

  public String getSelectedEffects() {
    return skillTableViewController.getSelectedEffects();
  }

  public ComboBox<String> getInsertComboBox() {
    return insertComboBox;
  }

  public static Config getConfig() {
    return config;
  }

  public void setNoteText(String text) {
    noteTextArea.setText(text);
  }

  /**
   * effectsセルの値を更新する。
   * @param selectedIndex
   * @param values
   */
  public void updateEffectsCell(int selectedIndex, double[] values) {
    skillTableViewController.updateEffectsCell(selectedIndex, values);
  }

  public void updateEffectsCellNonPushUndo(int selectedIndex, double[] values) {
    skillTableViewController.updateEffectsCellNonPushUndo(selectedIndex, values);
  }

  public void updateEffectsCell(String newText) {
    skillTableViewController.updateEffectsCell(newText);
  }

  void openFiles() {
    if (config.autoInput) {
      if (config.projectIsSelected) {
        File dir = new File(config.projectPath);
        if (dir.exists()) {
          importProject(dir);
        }
        return;
      }
      if (config.inputIsSelected) {
        importFolder();
      }
    }
  }

}
