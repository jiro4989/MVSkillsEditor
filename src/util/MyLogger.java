package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
  private final Logger logger;
  private static final String DIR_PATH = "." + File.separator + "errors";
  private static final File DIR = new File(DIR_PATH);
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMDD");

  public MyLogger(String className) {
    logger = Logger.getLogger(className);
    logger.setLevel(Level.CONFIG);
    try {
    } catch (SecurityException e) {
      logger.log(Level.SEVERE, this.getClass().getName() + "エラー", e);
    }
  }

  public void log(Level severe, String string, Throwable thrown) {
    if (!DIR.exists()) {
      DIR.mkdirs();
    }

    Calendar calendar = Calendar.getInstance();
    File logFile = new File(
        DIR_PATH + File.separator + "log" + FORMAT.format(calendar.getTime()) + ".log");
    if (!logFile.exists()) {
      try {
        logFile.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

    try {
      FileHandler fh = new FileHandler(logFile.getPath());
      fh.setFormatter(new SimpleFormatter());
      logger.addHandler(fh);
    } catch (SecurityException | IOException e1) {
      e1.printStackTrace();
    }

    logger.log(severe, string, thrown);
  }
}
