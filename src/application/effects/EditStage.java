package application.effects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditStage extends Stage {
  public EditStage() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStage.fxml"));
      BorderPane root = (BorderPane) loader.load();
      Scene scene = new Scene(root, 600, 400);
      scene.getStylesheets()
          .add(getClass().getResource("/application/application.css").toExternalForm());
      setScene(scene);
      setTitle("使用効果");
      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}