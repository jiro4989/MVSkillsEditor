package application.tableview.icon;

import java.io.File;

import application.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IconIndexChooser extends Stage {
  private IconIndexChooserController controller;

  public IconIndexChooser(File iconFile, int iconIndex) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("IconIndexChooser.fxml"));
      VBox root = (VBox) loader.load();

      controller = (IconIndexChooserController) loader.getController();
      controller.setIconFile(iconFile);
      controller.setFocusGridPanePosition(iconIndex);

      Scene scene = new Scene(root);
      scene.getStylesheets()
          .add(getClass().getResource("/application/application.css").toExternalForm());
      setScene(scene);
      setTitle("アイコン選択");
      setResizable(false);
      sizeToScene();
      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);
      setOnCloseRequest(e -> closeAction());
      setX(MainController.getConfig().iconIndexChooserX);
      setY(MainController.getConfig().iconIndexChooserY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public IconIndexChooserController getController() {
    return controller;
  }

  void closeAction() {
    MainController.getConfig().iconIndexChooserX = getX();
    MainController.getConfig().iconIndexChooserY = getY();
  }

}
