package io.electrum.sdk.masking2;

/**
 * Interface that will be used by various maskers (such as {@link MaskPan}) that define rules for how to mask the given
 * string.
 */
public abstract class Masker {
   // globally disable masking if this is set. Typically toggled via configuration in apps making use of this library
   private static boolean disabled = false;

   public abstract String mask(String value);

   public static final boolean isDisabled() {
      return disabled;
   }

   public static final void setDisabled(boolean disabled) {
      Masker.disabled = disabled;
   }
}
