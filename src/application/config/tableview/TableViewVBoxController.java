package application.config.tableview;

import application.MainController;
import application.config.Config;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

public class TableViewVBoxController {
  private Config config;
  public static final String DESCRIPTION = "テーブルのフォントサイズやセルサイズを変更します。";

  @FXML private BorderPane fontSizeBorderPane;
  @FXML private ComboBox<Integer> fontSizeConboBox;
  @FXML private BorderPane cellSizeBorderPane;
  @FXML private ComboBox<Integer> cellSizeConboBox;

  @FXML
  private void initialize() {
    config = MainController.getConfig();
    fontSizeConboBox.setValue(config.tableViewFontSize);
    cellSizeConboBox.setValue(config.tableCellHeight);
  }

  public int getFontSize() {
    return fontSizeConboBox.getValue();
  }

  public int getCellHeight() {
    return cellSizeConboBox.getValue();
  }
}
