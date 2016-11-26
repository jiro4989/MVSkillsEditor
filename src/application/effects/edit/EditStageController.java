package application.effects.edit;

import java.util.ArrayList;
import java.util.List;

import application.effects.EffectsTableViewBorderPaneController;
import application.effects.edit.strategy.EditStrategyManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jiro.lib.javafx.scene.control.CustomedComboBox;
import jiro.lib.javafx.scene.control.NumericTextField;
import util.dictionary.Ability;

public class EditStageController {
  private EffectsTableViewBorderPaneController controller;
  private ToggleGroup toggleGroup;
  private EditStrategyManager editStrategyManager = new EditStrategyManager();

  private ListViewManager addStateListViewManager;
  private ListViewManager releaseStateListViewManager;

  private ListViewManager learningListViewManager;
  private ListViewManager commonEventListViewManager;

  @FXML private BorderPane root;
  @FXML private TabPane tabPane;

  // **************************************************
  // 回復タブ
  // **************************************************
  @FXML private GridPane healGridPane;
  @FXML private RadioButton hpRadioButton;
  private NumericTextField hpPercentageTextField = new NumericTextField("0", -100, 100, 0);
  private NumericTextField hpPlusTextField = new NumericTextField("0", -999999, 999999, 0);

  @FXML private RadioButton mpRadioButton;
  private NumericTextField mpPlusTextField = new NumericTextField("0", -999999, 999999, 0);
  private NumericTextField mpPercentageTextField = new NumericTextField("0", -100, 100, 0);

  @FXML private RadioButton tpRadioButton;
  private NumericTextField tpTextField = new NumericTextField("0", 0, 100, 0);

  // **************************************************
  // ステートタブ
  // **************************************************
  @FXML private GridPane stateGridPane;
  @FXML private RadioButton addStateRadioButton;
  private NumericTextField addStateTextField = new NumericTextField("0", 0, 100, 0);
  @FXML private RadioButton releaseStateRadioButton;
  private NumericTextField releaseStateTextField = new NumericTextField("0", 0, 100, 0);

  @FXML private ListView<String> stateListView;
  @FXML private TextField stateFilterTextField;

  // **************************************************
  // 能力値タブ
  // **************************************************
  @FXML private GridPane abilityGridPane;
  private static final double COMBO_BOX_WIDTH = 200.0;
  private static final int TEXT_FIELD_WIDTH = 1000;

  @FXML private RadioButton upRadioButton;
  private CustomedComboBox upComboBox = new CustomedComboBox(COMBO_BOX_WIDTH,
      Ability.getNameList());
  private NumericTextField upTextField = new NumericTextField("1", 1, TEXT_FIELD_WIDTH, 1);

  @FXML private RadioButton downRadioButton;
  private CustomedComboBox downComboBox = new CustomedComboBox(COMBO_BOX_WIDTH,
      Ability.getNameList());
  private NumericTextField downTextField = new NumericTextField("1", 1, TEXT_FIELD_WIDTH, 1);

  @FXML private RadioButton upReleaseRadioButton;
  private CustomedComboBox upReleaseComboBox = new CustomedComboBox(COMBO_BOX_WIDTH,
      Ability.getNameList());

  @FXML private RadioButton downReleaseRadioButton;
  private CustomedComboBox downReleaseComboBox = new CustomedComboBox(COMBO_BOX_WIDTH,
      Ability.getNameList());

  // **************************************************
  // その他タブ
  // **************************************************
  @FXML private GridPane othersGridPane;
  @FXML private RadioButton specialEffectRadioButton;
  private CustomedComboBox specialEffectComboBox = new CustomedComboBox(COMBO_BOX_WIDTH, "逃げる");
  @FXML private RadioButton growthRadioButton;
  private CustomedComboBox growthComboBox = new CustomedComboBox(200, Ability.getNameList());
  private NumericTextField growthTextField = new NumericTextField("1", 1, TEXT_FIELD_WIDTH, 1);

  @FXML private RadioButton learningRadioButton;
  @FXML private ListView<String> learningListView;
  @FXML private TextField learningFilterTextField;

  @FXML private RadioButton commonEventRadioButton;
  @FXML private ListView<String> commonEventListView;
  @FXML private TextField commonEventFilterTextField;

  // **************************************************
  // OK・キャンセルボタン
  // **************************************************
  @FXML private Button addButton;
  @FXML private Button okButton;
  @FXML private Button cancelButton;

  @FXML
  private void initialize() {
    // 回復タブ
    healGridPane.add(hpPercentageTextField, 1, 0);
    healGridPane.add(hpPlusTextField, 4, 0);
    healGridPane.add(mpPercentageTextField, 1, 1);
    healGridPane.add(mpPlusTextField, 4, 1);
    healGridPane.add(tpTextField, 1, 2);

    // ステートタブ
    stateGridPane.add(addStateTextField, 0, 1);
    stateGridPane.add(releaseStateTextField, 0, 3);

    // 能力値タブ
    abilityGridPane.add(upComboBox, 1, 0);
    abilityGridPane.add(upTextField, 2, 0);

    abilityGridPane.add(downComboBox, 1, 1);
    abilityGridPane.add(downTextField, 2, 1);

    abilityGridPane.add(upReleaseComboBox, 1, 2);
    abilityGridPane.add(downReleaseComboBox, 1, 3);

    // その他タブ
    othersGridPane.add(specialEffectComboBox, 1, 0);
    othersGridPane.add(growthComboBox, 1, 1);
    othersGridPane.add(growthTextField, 2, 1);

    toggleGroup = new ToggleGroup();
    hpRadioButton.setToggleGroup(toggleGroup);
    mpRadioButton.setToggleGroup(toggleGroup);
    tpRadioButton.setToggleGroup(toggleGroup);
    addStateRadioButton.setToggleGroup(toggleGroup);
    releaseStateRadioButton.setToggleGroup(toggleGroup);
    upRadioButton.setToggleGroup(toggleGroup);
    downRadioButton.setToggleGroup(toggleGroup);
    upReleaseRadioButton.setToggleGroup(toggleGroup);
    downReleaseRadioButton.setToggleGroup(toggleGroup);
    specialEffectRadioButton.setToggleGroup(toggleGroup);
    growthRadioButton.setToggleGroup(toggleGroup);
    learningRadioButton.setToggleGroup(toggleGroup);
    commonEventRadioButton.setToggleGroup(toggleGroup);
  }

  @FXML
  private void rootOnKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      cancelButtonOnClicked();
    }
  }

  @FXML
  private void addButtonOnClicked() {
    controller.addEffects(getValues());
  }

  @FXML
  private void okButtonOnClicked() {
    controller.updateEffects(getValues());
    okButton.getScene().getWindow().hide();
  }

  private double[] getValues() {
    int strategyIndex = getSelectedRadioButtonIndex();
    changeStrategy(strategyIndex);
    return editStrategyManager.getValues();
  }

  @FXML
  private void cancelButtonOnClicked() {
    cancelButton.getScene().getWindow().hide();
  }

  private int getSelectedRadioButtonIndex() {
    ObservableList<Toggle> toggleList = toggleGroup.getToggles();
    int toggleSize = toggleList.size();
    for (int i = 0; i < toggleSize; i++) {
      Toggle toggle = toggleList.get(i);
      Toggle selectedToggle = toggleGroup.getSelectedToggle();
      if (selectedToggle.equals(toggle)) {
        return i;
      }
    }
    return 0;
  }

  /**
   * 最初の選択状態やタブを切り替える。
   * 全ての値が-1の場合、初期値をセットする。
   * @param codeId
   * @param dataId
   * @param value1
   * @param value2
   * @param commonEventList
   * @param stateList
   * @param skillList
   */
  public void setInitialValues(int codeId, int dataId, double value1, double value2,
      List<String> skillList, List<String> stateList, List<String> commonEventList) {

    List<String> newAddStateList = new ArrayList<>(stateList);
    newAddStateList.add(0, null);
    List<String> newReleaseStateList = new ArrayList<>(stateList);

    addStateListViewManager = new ListViewManager(stateListView, stateFilterTextField,
        newAddStateList, -1);
    releaseStateListViewManager = new ListViewManager(stateListView, stateFilterTextField,
        newReleaseStateList);
    learningListViewManager = new ListViewManager(learningListView, learningFilterTextField,
        skillList);
    commonEventListViewManager = new ListViewManager(commonEventListView,
        commonEventFilterTextField, commonEventList);

    if (codeId == -1 &&
        dataId == -1 &&
        value1 == -1 &&
        value2 == -1) {
      tabPane.getSelectionModel().select(0);
      hpRadioButton.setSelected(true);
      changeDisable();
    } else {
      int tabIndex = codeId / 10 - 1;
      tabPane.getSelectionModel().select(tabIndex);

      int radioIndex = editStrategyManager.calculateStrategyIndex(codeId);
      toggleGroup.getToggles().get(radioIndex).setSelected(true);
      changeDisable();
      editStrategyManager.setValues(dataId, value1, value2);
      if (addStateRadioButton.isSelected()) {
        addStateRadioButtonOnAction();
        editStrategyManager.setValues(dataId, value1, value2);
      } else if (releaseStateRadioButton.isSelected()) {
        releaseStateRadioButtonOnAction();
        editStrategyManager.setValues(dataId-1, value1, value2);
      } else if (learningRadioButton.isSelected()) {
        editStrategyManager.setValues(dataId-1, value1, value2);
      } else if (commonEventRadioButton.isSelected()) {
        editStrategyManager.setValues(dataId-1, value1, value2);
      }
    }
  }

  @FXML
  private void changeDisable() {
    setDisableAll();
    int strategyIndex = getSelectedRadioButtonIndex();
    changeStrategy(strategyIndex);
    editStrategyManager.changeDisable();
  }

  private void setDisableAll() {
    hpPercentageTextField.setDisable(true);
    hpPlusTextField.setDisable(true);
    mpPercentageTextField.setDisable(true);
    mpPlusTextField.setDisable(true);
    tpTextField.setDisable(true);

    stateListView.setDisable(true);
    addStateTextField.setDisable(true);
    releaseStateTextField.setDisable(true);

    upComboBox.setDisable(true);
    upTextField.setDisable(true);
    downComboBox.setDisable(true);
    downTextField.setDisable(true);
    upReleaseComboBox.setDisable(true);
    downReleaseComboBox.setDisable(true);

    specialEffectComboBox.setDisable(true);
    growthComboBox.setDisable(true);
    growthTextField.setDisable(true);
    learningListViewManager.setDisable(true);
    commonEventListViewManager.setDisable(true);
  }

  private void changeStrategy(int strategyIndex) {
    editStrategyManager.changeStrategy(strategyIndex,
        hpPercentageTextField, hpPlusTextField,
        mpPercentageTextField, mpPlusTextField,
        tpTextField,
        addStateListViewManager, addStateTextField,
        releaseStateListViewManager, releaseStateTextField,
        upComboBox, upTextField,
        downComboBox, downTextField,
        upReleaseComboBox, downReleaseComboBox,
        specialEffectComboBox,
        growthComboBox, growthTextField,
        learningListViewManager,
        commonEventListViewManager);
  }

  public void setController(EffectsTableViewBorderPaneController aController) {
    controller = aController;
  }

  @FXML
  private void addStateRadioButtonOnAction() {
    stateListView.setItems(addStateListViewManager.getFilteredList());
    changeDisable();
  }

  @FXML
  private void releaseStateRadioButtonOnAction() {
    stateListView.setItems(releaseStateListViewManager.getFilteredList());
    changeDisable();
  }
}
