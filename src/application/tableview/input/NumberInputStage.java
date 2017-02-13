package application.tableview.input;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NumberInputStage extends Stage {
  private NumberInputStageController controller;

  public NumberInputStage(int recordsCount) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("NumberInputStage.fxml"));
      BorderPane root = (BorderPane) loader.load();

      controller = (NumberInputStageController) loader.getController();
      controller.setValue(recordsCount);

      Scene scene = new Scene(root, 250, 120);
      scene.getStylesheets()
          .add(getClass().getResource("/application/application.css").toExternalForm());
      setScene(scene);
      setResizable(false);
      setTitle("最大数の変更");

      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);

      setOnCloseRequest(e -> controller.cancel());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getValue() {
    return controller.getValue();
  }
}
