package util.json;

import util.UtilDouble;

public class JsonEffects {
    public int code;
    public int dataId;
    public double value1;
    public double value2;

    public JsonEffects(double[] values) {
      this.code = (int) values[0];
      this.dataId = (int) values[1];
      this.value1 = values[2];
      this.value2 = values[3];
    }

    public JsonEffects(Double[] values) {
      this(UtilDouble.convertDoubleWrapperToPrimitive(values));;
    }
}
