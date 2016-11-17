package application.effects;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import jiro.lib.javafx.scene.control.NumericTextField;

public class EditStageController {
  // **************************************************
  // 回復タブ
  // **************************************************
  private ToggleGroup toggleGroup = new ToggleGroup();
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

  // **************************************************
  // 能力値タブ
  // **************************************************
  @FXML
  GridPane abilityGridPane;
  private static final String[] parameterItems = {
      "最大HP", "最大MP", "攻撃力", "防御力", "魔法力", "魔法防御", "敏捷性", "運",
  };

  @FXML
  RadioButton upRadioButton;
  private CustomedComboBox upComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField upTextField = new NumericTextField("1", 1, 1000, 1);

  @FXML
  RadioButton downRadioButton;
  private CustomedComboBox downComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField downTextField = new NumericTextField("1", 1, 1000, 1);

  @FXML
  RadioButton upReleaseRadioButton;
  private CustomedComboBox upReleaseComboBox = new CustomedComboBox(200, parameterItems);

  @FXML
  RadioButton downReleaseRadioButton;
  private CustomedComboBox downReleaseComboBox = new CustomedComboBox(200, parameterItems);

  // **************************************************
  // その他タブ
  // **************************************************
  @FXML
  GridPane othersGridPane;
  @FXML
  RadioButton specialEffectRadioButton;
  private CustomedComboBox specialEffectComboBox = new CustomedComboBox(200, "逃げる");
  @FXML
  RadioButton growthRadioButton;
  private CustomedComboBox growthComboBox = new CustomedComboBox(200, parameterItems);
  private NumericTextField growthTextField = new NumericTextField("1", 1, 1000, 1);
  @FXML
  RadioButton learningRadioButton;
  @FXML
  ListView<String> learningListView;
  @FXML
  RadioButton commonEventRadioButton;
  @FXML
  ListView<String> commonEventListView;

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
    hpRadioButton.setSelected(true);

    // ラジオボタンをクリックすると他のパネルを選択不可にする機能を実装する。
  }
}
