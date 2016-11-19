package application.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfigStage extends Stage {
  public ConfigStage() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigStage.fxml"));
      BorderPane root = (BorderPane) loader.load();
      Scene scene = new Scene(root, 800, 500);
      scene.getStylesheets()
          .add(getClass().getResource("/application/application.css").toExternalForm());
      setScene(scene);
      setTitle("設定");
      setMinWidth(scene.getWidth());
      setMinHeight(scene.getHeight());
      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
