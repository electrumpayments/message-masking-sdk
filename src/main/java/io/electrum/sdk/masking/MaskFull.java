package io.electrum.sdk.masking;

/**
 * Returns a single masking character if the field is not empty. A null value will return null.
 * <p>
 * eg. 1234 will be printed as * (where * is the masking character). <br>
 */
public class MaskFull extends Masker {
   @Override
   public String mask(String value) {
      if (value == null) {
         return null;
      }

      if (isDisabled()) {
         return value;
      }

      return MaskingUtils.MASKING_CHAR;
   }
}
