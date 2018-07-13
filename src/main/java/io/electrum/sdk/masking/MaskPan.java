package io.electrum.sdk.masking;

import org.apache.commons.lang.StringUtils;

/**
 * Masker for card numbers. Print the first 6 and the last 4 characters of the PAN, mask all details in between.
 * <p>
 * eg. 1111222233334444 will be printed as 111122******4444 (where * is the masking character)
 */
public class MaskPan extends Masker {
   private static final int maskingStart = 6;
   private static final int maskingEnd = 4;

   @Override
   public String mask(String value) {
      if (value == null) {
         return null;
      }

      if (isDisabled()) {
         return value;
      }

      if (value.length() < 10) {
         return StringUtils.repeat(MaskingUtils.MASKING_CHAR, value.length());
      }

      return obfuscatePan(value);
   }

   private String obfuscatePan(String pan) {
      int maskingLength = pan.length() - maskingStart - maskingEnd;

      StringBuilder sb = new StringBuilder();

      sb.append(pan.substring(0, maskingStart));
      sb.append(StringUtils.repeat(MaskingUtils.MASKING_CHAR, maskingLength));
      sb.append(pan.substring(pan.length() - maskingEnd));

      return sb.toString();
   }
}
