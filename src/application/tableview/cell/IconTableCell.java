package application.tableview.cell;

import java.util.List;

import application.tableview.Skill;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class IconTableCell extends TableCell<Skill, String> {
  private HBox hBox;
  private ImageView imageView;
  private Label label;
  private static List<Image> iconImageList;

  public IconTableCell() {
    super();
    imageView = new ImageView();
    label = new Label();

    hBox = new HBox();
    hBox.setSpacing(10);
    hBox.getChildren().add(imageView);
    hBox.getChildren().add(label);
  }

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null) {
      int iconIndex = Integer.parseInt(item);
      imageView.setImage(iconImageList.get(iconIndex));
      label.setText(item);
      setGraphic(hBox);
    }
  }

  public static void setIconImageList(List<Image> anIconImageList) {
    iconImageList = anIconImageList;
  }
}
