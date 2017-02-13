package application.tableview.cell;

import application.tableview.Skill;
import javafx.scene.control.TableCell;

public class BooleanTableCell extends TableCell<Skill, String> {
  private static final String BOLD = "bold;";
  private static final String NORMAL = "normal;";

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null) {
      setText(item);
      String weight = item.equals("あり") ? BOLD : NORMAL;
      setStyle("-fx-font-weight:" + weight);
      return;
    }
    setText(null);
  }
}
