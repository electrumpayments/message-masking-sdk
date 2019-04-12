package io.electrum.sdk.masking2;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MaskingTest {

   private static final MaskFull maskFull = new MaskFull();
   private static final MaskAll maskAll = new MaskAll();
   private static final MaskPan maskPan = new MaskPan();
   private static final MaskTrack2 maskTrack2 = new MaskTrack2();

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
   private static final String track2MaskedExpected = "111122" + StringUtils.repeat(MaskingUtils.MASKING_CHAR, 6)
         + "4444=" + MaskingUtils.MASKING_CHAR;

   @Test
   public void testFieldsPopulated() throws Throwable {
      Assert.assertEquals(maskFull.mask(maskingStr), fullMaskedExpected);
      Assert.assertEquals(maskAll.mask(maskingStr), allMaskedExpected);
      Assert.assertEquals(maskPan.mask(maskingStr), panMaskedExpected);

      Assert.assertEquals(maskTrack2.mask(track2Str), track2MaskedExpected);
   }

   @Test
   public void testFieldsNull() throws Throwable {
      Assert.assertEquals(maskFull.mask(emptyStr), fullMaskedExpected);
      Assert.assertEquals(maskAll.mask(emptyStr), emptyStr);
      Assert.assertEquals(maskPan.mask(emptyStr), emptyStr);
   }

   @Test
   public void testShortPan() throws Throwable {
      Assert.assertEquals(maskAll.mask(shortStr), allMaskedShortExpected);
      Assert.assertEquals(
            maskPan.mask(shortStr),
            maskAll.mask(shortStr));
   }

   @Test
   public void testNoFieldSeparatorTrack2() throws Throwable {
      Assert.assertEquals(maskTrack2.mask(maskingStr), allMaskedExpected);
   }

   @Test
   public void testEmptyFieldTrack2() throws Throwable {
      Assert.assertEquals(maskTrack2.mask(emptyStr), emptyStr);
   }

   @Test
   public void testShortFieldTrack2() throws Throwable {
      Assert.assertEquals(maskTrack2.mask(shortStr), allMaskedShortExpected);
   }

   @Test void testPanLengthTen() throws Throwable {
      String lengthTenPan = "0123456789";
      String maskedLengthTenPan = "**********";
      Assert.assertEquals(maskPan.mask(lengthTenPan), maskedLengthTenPan);

      String lengthElevenPan = "01234567891";
      String maskedLengthElevenPan = "012345*7891";
      Assert.assertEquals(maskPan.mask(lengthElevenPan), maskedLengthElevenPan);
   }
}
