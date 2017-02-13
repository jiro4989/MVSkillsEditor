package util;

import java.util.stream.IntStream;

public class UtilDouble {
  public static Double[] convertDoublePrimitiveToWrapper(double[] primitives) {
    Double[] wrappers = new Double[primitives.length];
    IntStream.range(0, wrappers.length).forEach(i -> wrappers[i] = Double.valueOf(primitives[i]));
    return wrappers;
  }

  public static double[] convertDoubleWrapperToPrimitive(Double[] wrappers) {
    double[] primitives = new double[wrappers.length];
    IntStream.range(0, primitives.length).forEach(i -> primitives[i] = wrappers[i].doubleValue());
    return primitives;
  }
}
