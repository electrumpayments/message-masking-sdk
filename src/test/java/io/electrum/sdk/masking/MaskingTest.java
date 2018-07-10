package io.electrum.sdk.masking;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MaskingTest {

   private static final int headerLength = 38;

   private static final String maskingStr = "StringToBeMasked";
   private static final String emptyStr = "";
   private static final String shortStr = "StringTo";
   private static final String track2Str = "1111222233334444=1710=";

   private static final String allMaskedExpected = StringUtils.repeat(MaskingUtils.MASKING_CHAR, maskingStr.length());
   private static final String allMaskedShortExpected =
         StringUtils.repeat(MaskingUtils.MASKING_CHAR, shortStr.length());
   private static final String fullMaskedExpected = MaskingUtils.MASKING_CHAR;
   private static final String panMaskedExpected = "String"
         + StringUtils.repeat(MaskingUtils.MASKING_CHAR, maskingStr.length() - 6 - 4) + "sked";
   private static final String nullInputExpected = "[not set]";
   private static final String track2MaskedExpected = "111122" + StringUtils.repeat(MaskingUtils.MASKING_CHAR, 6)
         + "4444=" + MaskingUtils.MASKING_CHAR;

   @Test
   public void testFieldsPopulated() throws Throwable {
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringNotMasked.print(maskingStr, 0)), maskingStr);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringFullMasked.print(maskingStr, 0)), fullMaskedExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringAllMasked.print(maskingStr, 0)), allMaskedExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringPanMasked.print(maskingStr, 0)), panMaskedExpected);

      Assert.assertEquals(getActualValue(MaskingTestUtils.stringTrack2Masked.print(track2Str, 0)), track2MaskedExpected);
   }

   @Test
   public void testFieldsNull() throws Throwable {
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringNotMasked.print(emptyStr, 0)), emptyStr);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringFullMasked.print(emptyStr, 0)), fullMaskedExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringAllMasked.print(emptyStr, 0)), emptyStr);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringPanMasked.print(emptyStr, 0)), emptyStr);
   }

   @Test
   public void testShortPan() throws Throwable {
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringAllMasked.print(shortStr, 0)), allMaskedShortExpected);
      Assert.assertEquals(
            getActualValue(MaskingTestUtils.stringPanMasked.print(shortStr, 0)),
            getActualValue(MaskingTestUtils.stringAllMasked.print(shortStr, 0)));
   }

   @Test(expectedExceptions = IllegalStateException.class)
   public void testNoFieldSeparatorTrack2() throws Throwable {
      MaskingTestUtils.stringTrack2Masked.print(maskingStr, 0);
   }

   @Test(expectedExceptions = IllegalStateException.class)
   public void testEmptyFieldTrack2() throws Throwable {
      MaskingTestUtils.stringTrack2Masked.print(emptyStr, 0);
   }

   @Test(expectedExceptions = IllegalStateException.class)
   public void testShortFieldTrack2() throws Throwable {
      MaskingTestUtils.stringTrack2Masked.print(shortStr, 0);
   }

   @Test
   public void testNulls() throws Throwable {
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringNotMasked.print(null, 0)), nullInputExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringFullMasked.print(null, 0)), nullInputExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringAllMasked.print(null, 0)), nullInputExpected);
      Assert.assertEquals(getActualValue(MaskingTestUtils.stringPanMasked.print(null, 0)), nullInputExpected);
   }

   private static String getActualValue(String fieldDefinitionPrintout) {
      return fieldDefinitionPrintout.substring(headerLength).trim();
   }
}
