package application.effects;

import java.io.IOException;

import application.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class EffectsTableViewBorderPane extends BorderPane {
  private EffectsTableViewBorderPaneController controller;
  private MainController mainController;

  public EffectsTableViewBorderPane(MainController aMainController) {
    super();
    mainController = aMainController;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EffectsTableViewBorderPane.fxml"));
      BorderPane root = (BorderPane) loader.load();
      controller = (EffectsTableViewBorderPaneController) loader.getController();
      controller.setMainController(mainController);
      setCenter(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public EffectsTableViewBorderPaneController getController() {
    return controller;
  }
}
