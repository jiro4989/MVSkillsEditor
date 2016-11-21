package application.effects.edit;

import java.util.List;

import application.effects.EffectsTableViewBorderPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditStage extends Stage {
  public EditStage(EffectsTableViewBorderPaneController aController,
      int codeId, int dataId, double value1, double value2, List<String> skillList,
      List<String> stateList, List<String> commonEventList) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStage.fxml"));
      BorderPane root = (BorderPane) loader.load();

      EditStageController controller = (EditStageController) loader.getController();
      controller.setInitialValues(codeId, dataId, value1, value2, skillList, stateList,
          commonEventList);
      controller.setController(aController);

      Scene scene = new Scene(root, 500, 500);
      scene.getStylesheets()
          .add(getClass().getResource("/application/application.css").toExternalForm());
      setScene(scene);
      setTitle("使用効果");
      setMinWidth(scene.getWidth());
      setMinHeight(scene.getHeight());
      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
