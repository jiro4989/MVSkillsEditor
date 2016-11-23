package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class UtilIconImage {
  /**
   * アイコン画像の幅
   */
  public static final int ICON_WIDTH = 32;
  /**
   * アイコン画像の横の列数
   */
  public static final int COLUMN_COUNT = 16;

  /**
   * トリミングしたアイコン画像のリストを作成する。
   * @param file アイコン画像のファイル
   * @return 作成したアイコン画像のリスト
   */
  public static List<Image> makeIconImageList(File file) {
    Image image = new Image("file:" + file.getPath());
    double height = image.getHeight();
    int rowCount = (int) (height / ICON_WIDTH);
    int listSize = COLUMN_COUNT * rowCount;
    List<Image> imageList = new ArrayList<>(listSize);

    IntStream.range(0, listSize)
        .forEach(iconIndex -> {
          PixelReader pix = image.getPixelReader();
          double x = iconIndex % COLUMN_COUNT * ICON_WIDTH;
          double y = iconIndex / COLUMN_COUNT * ICON_WIDTH;
          WritableImage iconImage = new WritableImage(pix, (int) x, (int) y, ICON_WIDTH, ICON_WIDTH);
          imageList.add(iconImage);
        });
    return imageList;
  }
}
