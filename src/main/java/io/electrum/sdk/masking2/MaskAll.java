package io.electrum.sdk.masking2;

import org.apache.commons.lang.StringUtils;

/**
 * Masking that will replace every char in the string with the masking char.
 * <p>
 * eg. 12345 will be masked to *****
 */
public class MaskAll extends Masker {
   @Override
   public String mask(String value) {
      if (value == null) {
         return null;
      }
      
      if (isDisabled()) {
         return value;
      }
      
      return StringUtils.repeat(MaskingUtils.MASKING_CHAR, value.length());
   }
}
