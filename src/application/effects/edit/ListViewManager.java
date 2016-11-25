package application.effects.edit;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListViewManager {
  private ListView<String> listView;
  private TextField filterTextField;
  private FilteredList<String> filteredList;

  public ListViewManager(ListView<String> aListView, TextField aFilteredTextField,
      List<String> list) {
    listView = aListView;
    filterTextField = aFilteredTextField;
    filteredList = makeFilteredList(list);
    listView.setItems(filteredList);

    filterTextField.textProperty().addListener(obs -> {
      String filter = filterTextField.getText();
      if (filter == null || filter.length() == 0) {
        filteredList.setPredicate(s -> true);
      } else {
        filteredList.setPredicate(s -> s.contains(filter));
      }
      listView.getSelectionModel().select(0);
    });
  }

  private FilteredList<String> makeFilteredList(List<String> list) {
    AtomicInteger index = new AtomicInteger();
    list = list.stream()
        .map(s -> String.format("%04d: ", index.getAndIncrement()) + s)
        .collect(Collectors.toList());
    list.remove(0);

    ObservableList<String> obsList = FXCollections.observableArrayList(list);
    return new FilteredList<>(obsList, s -> true);
  }

  public ListView<String> getListView() {
    return listView;
  }

  public double[] getValue(double code) {
    String text = listView.getSelectionModel().getSelectedItem();
    text = text.split(":")[0];
    double[] values = {
        code,
        Double.parseDouble(text),
        0.0,
        0.0,
    };
    return values;
  }

  public void setDisable(boolean disable) {
    listView.setDisable(disable);
    filterTextField.setDisable(disable);
  }
}
