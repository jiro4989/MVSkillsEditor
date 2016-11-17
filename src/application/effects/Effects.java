package application.effects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Effects {
  private StringProperty type;
  private StringProperty content;

  public Effects(String type, String content) {
    this.type = new SimpleStringProperty(type);
    this.content = new SimpleStringProperty(content);
  }

  // **************************************************
  // Getter
  // **************************************************
  public StringProperty typeProperty() {
    return type;
  }

  public StringProperty contentProperty() {
    return content;
  }

  // **************************************************
  // Setter
  // **************************************************
  public void setType(String type) {
    this.type = new SimpleStringProperty(type);
  }

  public void setContent(String content) {
    this.content = new SimpleStringProperty(content);
  }
}
