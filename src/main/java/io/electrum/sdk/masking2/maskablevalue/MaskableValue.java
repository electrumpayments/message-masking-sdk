package io.electrum.sdk.masking2.maskablevalue;

import java.util.Objects;

/**
 * This object may be used in APIs to allow downstream to dictate which fields should be masked.
 * <p>
 * Usually used in a general use map of string keys and string values.
 */
public class MaskableValue {
   private String value;
   private boolean sensitive = false;
   private Storage storage = Storage.STORED_CLEAR;

   public MaskableValue() {
   }

   public MaskableValue(String value, boolean isSensitive) {
      this.value = value;
      this.sensitive = isSensitive;
   }

   public MaskableValue(String value, boolean isSensitive, Storage storage) {
      this.value = value;
      this.sensitive = isSensitive;
      this.storage = storage;
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

   public Storage getStorage() {
      return storage;
   }

   public void setStorage(Storage storage) {
      this.storage = storage;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      MaskableValue that = (MaskableValue) o;
      return sensitive == that.sensitive && Objects.equals(value, that.value) && storage == that.storage;
   }

   @Override
   public int hashCode() {
      return Objects.hash(value, sensitive, storage);
   }
}
