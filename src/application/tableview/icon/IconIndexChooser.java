package application.tableview.icon;

import java.io.File;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jiro.lib.java.util.PropertiesHundler;

public class IconIndexChooser extends Stage {
  private PropertiesHundler prop = new PropertiesHundler("icon-index-chooser");
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
      setWindowPosition();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public IconIndexChooserController getController() {
    return controller;
  }

  public void setWindowPosition() {
    if (prop.exists()) {
      prop.load();
      double x = Double.parseDouble(prop.getValue("x"));
      double y = Double.parseDouble(prop.getValue("y"));
      setX(x);
      setY(y);
    }
  }

  void closeAction() {
    prop.setValue("x", "" + getX());
    prop.setValue("y", "" + getY());
    prop.write();
  }

}
