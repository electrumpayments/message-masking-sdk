package io.electrum.sdk.masking;

/**
 * Masker that simply returns the value as-is. No masking done.
 */
public class MaskNone extends Masker {
   @Override
   public String mask(String value) {
      return value;
   }
}
