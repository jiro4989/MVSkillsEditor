package util;

import java.util.stream.IntStream;

import javafx.collections.ObservableList;

public class UtilInteger {
  public static int[] convertDoubleWrapperToPrimitive(ObservableList<Integer> wrappers) {
    int[] primitives = new int[wrappers.size()];
    IntStream.range(0, wrappers.size()).forEach(i -> primitives[i] = wrappers.get(i).intValue());
    return primitives;
  }
}
