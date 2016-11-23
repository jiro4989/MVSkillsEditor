package util;

import java.util.Set;

import application.tableview.Skill;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class UtilTableView {

  public static void bindScrollBar(TableView<Skill> leftTable, TableView<Skill> rightTable) {
    ScrollBar leftVerticalScrollBar = getScrollBar(leftTable, true);
    ScrollBar rightVerticalScrollBar = getScrollBar(rightTable, true);
    leftVerticalScrollBar.valueProperty().bindBidirectional(rightVerticalScrollBar.valueProperty());
  }

  private static ScrollBar getScrollBar(TableView<Skill> tableView, boolean vertical) {
    Set<Node> nodes = tableView.lookupAll(".scroll-bar");
    for (Node node : nodes) {
      if (node instanceof ScrollBar) {
        ScrollBar scrollBar = (ScrollBar) node;
        if (vertical && scrollBar.getOrientation() == Orientation.VERTICAL) {
          return scrollBar;
        } else if (!vertical && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
          return scrollBar;
        }
      }
    }
    throw new IllegalStateException("Not found!");
  }

}
