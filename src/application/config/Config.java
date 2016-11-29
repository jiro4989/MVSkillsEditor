package application.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * アプリケーションの設定情報などを保持するだけのクラス。
 * @author jiro
 */
public class Config {
  public boolean autoInput = true;
  public boolean projectIsSelected = false;
  public String projectPath = "";
  public boolean inputIsSelected = false;

  public boolean autoBackup = true;

  public int tableViewFontSize = 9;
  public int tableCellHeight = 50;

  private Properties prop;
  private static final String DIR_PATH = "./properties";
  private static final File PROP_FILE = new File(DIR_PATH + "/config.xml");

  public Config() {
    prop = new Properties();

    File dir = new File(DIR_PATH);
    dir.mkdirs();

    if (PROP_FILE.exists()) {
      load();
    }
  }

  private void load() {
    try {
      InputStream is = new FileInputStream(PROP_FILE);
      prop.loadFromXML(is);

      String autoInputStr = prop.getProperty("autoInput");
      autoInput = Boolean.valueOf(autoInputStr);
      projectPath = prop.getProperty("projectPath");
      String projectIsSelectedStr = prop.getProperty("projectIsSelected");
      projectIsSelected = Boolean.valueOf(projectIsSelectedStr);
      String inputIsSelectedStr = prop.getProperty("inputIsSelected");
      inputIsSelected = Boolean.valueOf(inputIsSelectedStr);

      String autoBackupStr = prop.getProperty("autoBackup");
      autoBackup = Boolean.valueOf(autoBackupStr);

      String fontSize = prop.getProperty("tableViewFontSize");
      tableViewFontSize = Integer.parseInt(fontSize);
      String height = prop.getProperty("tableCellHeight");
      tableCellHeight = Integer.parseInt(height);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (InvalidPropertiesFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void write() {
    prop.setProperty("autoInput", String.valueOf(autoInput));
    prop.setProperty("projectIsSelected", String.valueOf(projectIsSelected));
    prop.setProperty("projectPath", projectPath);
    prop.setProperty("inputIsSelected", String.valueOf(inputIsSelected));
    prop.setProperty("autoBackup", String.valueOf(autoBackup));
    prop.setProperty("tableViewFontSize", "" + tableViewFontSize);
    prop.setProperty("tableCellHeight", "" + tableCellHeight);

    try (OutputStream os = new FileOutputStream(PROP_FILE)) {
      prop.storeToXML(os, "設定画面で管理するプロパティ");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean exists() {
    return PROP_FILE.exists();
  }

  private static final String LINE = System.getProperty("line.separator");

  @Override
  public String toString() {
    return String.format(
        "autoInput: %s, projectPath: %s, " + LINE
            + "autoBackup: %s, " + LINE
            + "tableViewFontSize: %d, tableCellHeight: %d.",
        autoInput, projectPath,
        autoBackup,
        tableViewFontSize, tableCellHeight);
  }
}
