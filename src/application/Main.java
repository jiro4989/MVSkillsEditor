package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 実行クラス。
 * @author jiro
 * @version 1.2.1
 */
public class Main extends Application {
  public static final String TITLE = "MVSkillsEditor";
  public static final String VERSION = "ver 1.2.1";
  private MainController controller;

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
      BorderPane root = (BorderPane) loader.load();
      controller = (MainController) loader.getController();
      Scene scene = new Scene(root, 1280, 720);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle(TITLE);
      primaryStage.getIcons()
          .add(new Image(getClass().getResource("resources/logo.png").toExternalForm()));
      primaryStage.setMinWidth(1280);
      primaryStage.setMinHeight(720);
      primaryStage.setOnCloseRequest(e -> controller.closeAction());

      primaryStage.setX(MainController.getConfig().rootX);
      primaryStage.setY(MainController.getConfig().rootY);
      primaryStage.setWidth(MainController.getConfig().rootWidth);
      primaryStage.setHeight(MainController.getConfig().rootHeight);

      primaryStage.show();
      controller.setDividers();
      controller.openFiles();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
