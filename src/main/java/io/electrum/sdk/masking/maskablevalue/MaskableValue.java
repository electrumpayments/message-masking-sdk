package io.electrum.sdk.masking.maskablevalue;

/**
 * This object may be used in APIs to allow downstream to dictate which fields should be masked.
 * <p>
 * Usually used in a general use map of string keys and string values.
 */
public class MaskableValue {
   private String value;
   private boolean sensitive = false;

   public MaskableValue()   {
   }
   
   public MaskableValue(String value, boolean isSensitive)  {
      this.value = value;
      this.sensitive = isSensitive;
   }
   
   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public boolean isSensitive() {
      return sensitive;
   }

   public void setSensitive(boolean sensitive) {
      this.sensitive = sensitive;
   }
   
   @Override
   public int hashCode() {
      return (31 * (this.value.hashCode() + ((sensitive) ? booleanTrueHashCode : booleanFalseHashCode)));
   }
   
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      MaskableValue that = (MaskableValue) o;

      boolean isValueEquals = this.value.equals(that.getValue());
      boolean isSensitiveEquals = this.sensitive == (that.isSensitive());

      return isValueEquals && isSensitiveEquals;
   }
   
   // Booleans in Java only have 2 possible values, so initialise them once and reuse them.
   private static int booleanTrueHashCode = new Boolean(true).hashCode();
   private static int booleanFalseHashCode = new Boolean(false).hashCode();
}
