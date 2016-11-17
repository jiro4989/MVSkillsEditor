package application.effects;

import application.MainController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EffectsTableViewBorderPaneController {
  private MainController mainController;

  @FXML
  private TableView<Effects> effectsTableView = new TableView<>();
  @FXML
  private TableColumn<Effects, String> typeColumn = new TableColumn<>("type");
  @FXML
  private TableColumn<Effects, String> contentColumn = new TableColumn<>("content");

  @FXML
  private void initialize() {
    typeColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("type"));
    contentColumn.setCellValueFactory(new PropertyValueFactory<Effects, String>("content"));

    effectsTableView.getItems().add(new Effects("", ""));
    effectsTableView.setOnMouseClicked(click -> {
      if (click.getClickCount() == 2) {
        openEditStage();
      }
    });
  }

  /**
   * 使用効果の編集画面を開く
   */
  private void openEditStage() {
    EditStage editStage = new EditStage();
    editStage.showAndWait();
  }

  public void setMainController(MainController aMainController) {
    mainController = aMainController;
  }
}
