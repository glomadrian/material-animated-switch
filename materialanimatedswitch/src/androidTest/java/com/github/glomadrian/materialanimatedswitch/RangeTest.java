package com.github.glomadrian.materialanimatedswitch;

import android.test.AndroidTestCase;

/**
 * @author Adrián García Lomas
 */
public class RangeTest extends AndroidTestCase {

  /**
   * Formula testing Result := ((Input - InputLow) / (InputHigh - InputLow)) * (OutputHigh -
   * OutputLow) + OutputLow;
   *
   * the to range is 0-20
   * the form gange is 0-10
   * the result must be 10
   *
   * @throws Exception
   */
  public void testRange() throws Exception {
    int value = convertRange(5, 0, 20, 0, 10);

   int value2 =  convert(5, 0,35,0,255);

    assertTrue(value == 10);
    assertTrue(value2 == 10);

  }

  private int convertRange(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
    return ((value - fromLow) / (fromHigh - fromLow)) * (toHigh - toLow) + toLow;
  }

  private int convert(int value, int min1, int max1, int min2, int max2) {
    return min2 + ((value - min1) / (max1 - min1)) * (max2 - min2);
  }
}
