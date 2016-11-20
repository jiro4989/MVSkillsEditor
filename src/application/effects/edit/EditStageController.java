package application.effects.edit;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import jiro.lib.javafx.scene.control.CustomedComboBox;
import jiro.lib.javafx.scene.control.NumericTextField;

public class EditStageController {
  private ToggleGroup toggleGroup;

  @FXML
  private TabPane tabPane;

  // **************************************************
  // 回復タブ
  // **************************************************
  @FXML
  private GridPane healGridPane;
  @FXML
  private RadioButton hpRadioButton;
  private NumericTextField hpPercentageTextField = new NumericTextField("0", -100, 100, 0);
  private NumericTextField hpPlusTextField = new NumericTextField("0", -999999, 999999, 0);

  @FXML
  private RadioButton mpRadioButton;
  private NumericTextField mpPlusTextField = new NumericTextField("0", -999999, 999999, 0);
  private NumericTextField mpPercentageTextField = new NumericTextField("0", -100, 100, 0);

  @FXML
  private RadioButton tpRadioButton;
  private NumericTextField tpTextField = new NumericTextField("0", 0, 100, 0);

  // **************************************************
  // ステートタブ
  // **************************************************
  @FXML
  private GridPane stateGridPane;
  @FXML
  private RadioButton addStateRadioButton;
  private NumericTextField addStateTextField = new NumericTextField("0", 0, 100, 0);
  @FXML
  private RadioButton releaseStateRadioButton;
  private NumericTextField releaseStateTextField = new NumericTextField("0", 0, 100, 0);
  @FXML
  private ListView<String> stateListView;
  @FXML
  private TextField stateFilterTextField;

  // **************************************************
  // 能力値タブ
  // **************************************************
  @FXML
  private GridPane abilityGridPane;
  private static final String[] parameterItems = {
      "最大HP", "最大MP", "攻撃力", "防御力", "魔法力", "魔法防御", "敏捷性", "運",
  };

  @FXML
  private RadioButton upRadioButton;
  private CustomedComboBox upComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField upTextField = new NumericTextField("1", 1, 1000, 1);

  @FXML
  private RadioButton downRadioButton;
  private CustomedComboBox downComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField downTextField = new NumericTextField("1", 1, 1000, 1);

  @FXML
  private RadioButton upReleaseRadioButton;
  private CustomedComboBox upReleaseComboBox = new CustomedComboBox(200, parameterItems);

  @FXML
  private RadioButton downReleaseRadioButton;
  private CustomedComboBox downReleaseComboBox = new CustomedComboBox(200, parameterItems);

  // **************************************************
  // その他タブ
  // **************************************************
  @FXML
  private GridPane othersGridPane;
  @FXML
  private RadioButton specialEffectRadioButton;
  private CustomedComboBox specialEffectComboBox = new CustomedComboBox(200, "逃げる");
  @FXML
  private RadioButton growthRadioButton;
  private CustomedComboBox growthComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField growthTextField = new NumericTextField("1", 1, 1000, 1);
  @FXML
  private RadioButton learningRadioButton;
  @FXML
  private ListView<String> learningListView;
  @FXML
  private TextField learningFilterTextField;
  @FXML
  private RadioButton commonEventRadioButton;
  @FXML
  private ListView<String> commonEventListView;
  @FXML
  private TextField commonEventFilterTextField;

  // **************************************************
  // OK・キャンセルボタン
  // **************************************************
  @FXML
  private Button addButton;
  @FXML
  private Button okButton;
  @FXML
  private Button cancelButton;

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
  private void okButtonOnClicked() {
    okButton.getScene().getWindow().hide();
  }

  @FXML
  private void cancelButtonOnClicked() {
    cancelButton.getScene().getWindow().hide();
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
  public void setInitialValues(int codeId, int dataId, double value1, double value2, List<String> skillList, List<String> stateList, List<String> commonEventList) {
    learningListView.setItems(FXCollections.observableArrayList(skillList));
    stateListView.setItems(FXCollections.observableArrayList(stateList));
    commonEventListView.setItems(FXCollections.observableArrayList(commonEventList));

    if (codeId == -1 &&
        dataId == -1 &&
        value1 == -1 &&
        value2 == -1) {
      tabPane.getSelectionModel().select(0);
      hpRadioButton.setSelected(true);
    } else {
      int tabIndex = codeId / 10 - 1;
      int radioIndex = codeId % 10 - 1;
      // @formatter:off
      radioIndex =
            tabIndex == 0 ? radioIndex
          : tabIndex == 1 ? radioIndex + 3
          : tabIndex == 2 ? radioIndex + 5
          : radioIndex + 9;
      // @formatter:on

      tabPane.getSelectionModel().select(tabIndex);
      toggleGroup.getToggles().get(radioIndex).setSelected(true);
      setValues(radioIndex, dataId, value1, value2);
    }
  }

  /**
   * 選択されているラジオボタンのインデックスからセットする対象を切り替えて、
   * 値をセットする。
   * @param radioIndex 選択されているラジオボタンのインデックス
   * @param value1 Value1
   * @param value2 Value2
   */
  private void setValues(int radioIndex, int dataId, double value1, double value2) {
    if (radioIndex == 0) {
      // 回復タブ
      hpPercentageTextField.setText("" + (int) (value1 * 100));
      hpPlusTextField.setText("" + (int) value2);
    } else if (radioIndex == 1) {
      mpPercentageTextField.setText("" + (int) (value1 * 100));
      mpPlusTextField.setText("" + (int) value2);
    } else if (radioIndex == 2) {
      tpTextField.setText("" + (int) value1);
    } else if (radioIndex == 3) {
      // ステートタブ
      stateListView.getSelectionModel().select(dataId);
      addStateTextField.setText("" + (int)(value1 * 100));
    } else if (radioIndex == 4) {
      stateListView.getSelectionModel().select(dataId);
      releaseStateTextField.setText("" + (int)(value1 * 100));
    } else if (radioIndex == 5) {
      // 能力値タブ
      upComboBox.getSelectionModel().select(dataId);
      upTextField.setText("" + (int)value1);
    } else if (radioIndex == 6) {
      downComboBox.getSelectionModel().select(dataId);
      downTextField.setText("" + (int)value1);
    } else if (radioIndex == 7) {
      upReleaseComboBox.getSelectionModel().select(dataId);
    } else if (radioIndex == 8) {
      downReleaseComboBox.getSelectionModel().select(dataId);
    } else if (radioIndex == 9) {
      // その他タブ
      specialEffectComboBox.getSelectionModel().select(dataId);
    } else if (radioIndex == 10) {
      growthComboBox.getSelectionModel().select(dataId);
      growthTextField.setText("" + (int)value1);
    } else if (radioIndex == 11) {
      learningListView.getSelectionModel().select(dataId);
    } else if (radioIndex == 12) {
      commonEventListView.getSelectionModel().select(dataId);
    }
  }
}
