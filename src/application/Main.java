package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
  private static final String TITLE = "MVSkillsMaker ver1.0";
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
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
