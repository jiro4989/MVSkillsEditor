package application.tableview.icon;

import static util.UtilIconImage.COLUMN_COUNT;
import static util.UtilIconImage.ICON_WIDTH;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * アイコン選択画面のコントローラ。
 * RPGツクールMVでは横幅を増やしても、クリックしても範囲外は認識されない。
 * 縦幅を追加したら場合であれば、クリックは可能であるため、それに準拠する。
 * 公式配布画像では以下のとおり。
 * width = 512
 * height = 640
 * @author jiro
 */
public class IconIndexChooserController {
  private double imageHeight;
  private int initialIconIndex;

  @FXML private GridPane gridPane;
  @FXML private ImageView iconImageView;
  @FXML private GridPane focusGridPane;

  @FXML private Label iconIndexLabel;
  @FXML private Button okButton;
  @FXML private Button cancelButton;

  @FXML
  private void initialize() {
  }

  @FXML
  private void iconImageViewOnMouseDragged(MouseEvent event) {
    double x = event.getX();
    double y = event.getY();
    setFocusGrid(x, y);
  }

  @FXML
  private void iconImageViewOnMousePressed(MouseEvent event) {
    double x = event.getX();
    double y = event.getY();
    setFocusGrid(x, y);
  }

  @FXML
  private void focusGridPaneOnMouseClicked(MouseEvent event) {
    if (event.getClickCount() == 2) {
      okButtonOnClicked();
    }
  }

  @FXML
  private void okButtonOnClicked() {
    IconIndexChooser stage = (IconIndexChooser) okButton.getScene().getWindow();
    stage.closeAction();
    okButton.getScene().getWindow().hide();
  }

  @FXML
  private void cancelButtonOnClicked() {
    IconIndexChooser stage = (IconIndexChooser) okButton.getScene().getWindow();
    stage.closeAction();
    iconIndexLabel.setText("" + initialIconIndex);
    okButton.getScene().getWindow().hide();
  }

  public void setFocusGridPanePosition(int iconIndex) {
    initialIconIndex = iconIndex;
    int column = iconIndex % COLUMN_COUNT;
    int row = iconIndex / COLUMN_COUNT;
    focusGridPane.setLayoutX(column * ICON_WIDTH);
    focusGridPane.setLayoutY(row * ICON_WIDTH);

    iconIndexLabel.setText("" + iconIndex);
  }

  private void setFocusGrid(double x, double y) {
    if (x < 512 && y < imageHeight) {
      int column = (int) (x / ICON_WIDTH);
      int row = (int) (y / ICON_WIDTH);
      focusGridPane.setLayoutX(column * ICON_WIDTH);
      focusGridPane.setLayoutY(row * ICON_WIDTH);

      int index = row * COLUMN_COUNT + column;
      iconIndexLabel.setText("" + index);
    }
  }

  public int getIconIndex() {
    return Integer.parseInt(iconIndexLabel.getText());
  }

  public void setIconFile(File iconFile) {
    Image image = new Image("file:" + iconFile.getPath());
    double width = image.getWidth();
    double height = image.getHeight();
    imageHeight = height;
    gridPane.setPrefWidth(width);
    gridPane.setPrefHeight(height);
    iconImageView.setImage(image);
    iconImageView.setFitWidth(gridPane.getWidth());
    iconImageView.setFitHeight(gridPane.getHeight());
  }
}
