package application.tableview;

import java.io.File;

import application.tableview.icon.IconIndexChooserController;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class IconTableCell extends TableCell<Skill, String> {
  private static File iconImageFile;
  private HBox hBox;

  public IconTableCell() {
    super();
    hBox = new HBox();
    hBox.setSpacing(10);
  }

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null) {
      hBox.getChildren().clear();
      int iconIndex = Integer.parseInt(item);
      ImageView imageView = IconIndexChooserController.getSelectedIconImageView(iconImageFile, iconIndex);
      hBox.getChildren().add(imageView);
      hBox.getChildren().add(new Label(item));
      setGraphic(hBox);
    }
  }

  static void setIconImageFile(File file) {
    iconImageFile = file;
  }
}
