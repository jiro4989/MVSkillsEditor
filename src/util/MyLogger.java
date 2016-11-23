package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
  private final Logger logger;

  public MyLogger(String className) {
    logger = Logger.getLogger(className);
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
    logger.setLevel(Level.CONFIG);
    try {
      FileHandler fh = new FileHandler("log" + format.format(calendar.getTime()) + ".log");
      fh.setFormatter(new SimpleFormatter());
      logger.addHandler(fh);
    } catch (SecurityException | IOException e) {
      logger.log(Level.SEVERE, this.getClass().getName() + "エラー", e);
    }
  }

  public Logger getLogger() {
    return logger;
  }
}
