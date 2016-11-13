package application.tableview;

import java.io.IOException;

import application.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class SkillTableViewBorderPane extends BorderPane {
  private SkillTableViewBorderPaneController controller;
  private MainController mainController;

  public SkillTableViewBorderPane(MainController aMainController) {
    super();
    mainController = aMainController;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("SkillTableViewBorderPane.fxml"));
      BorderPane root = (BorderPane) loader.load();
      controller = (SkillTableViewBorderPaneController) loader.getController();
      controller.setMainController(mainController);
      setCenter(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public SkillTableViewBorderPaneController getController() {
    return controller;
  }
}