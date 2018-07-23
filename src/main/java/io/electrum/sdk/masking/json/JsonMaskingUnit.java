package io.electrum.sdk.masking.json;

import io.electrum.sdk.masking.Masker;

import java.util.Objects;

/**
 * Represents how a specific case of masking should be done within an JSON message. This includes:
 *    - JsonPath, which is used to select relevant nodes from the JSON message to be masked.
 *       (see https://github.com/json-path/JsonPath)
 *    - masker, which is used to mask the selected nodes' values.
 */
public class JsonMaskingUnit {

   private String jsonPath;
   private Masker masker;

   public JsonMaskingUnit(String jsonPath, Masker masker) {
      this.jsonPath = jsonPath;
      this.masker = masker;
   }

   public String getJsonPath() {
      return jsonPath;
   }

   public void setJsonPath(String jsonPath) {
      this.jsonPath = jsonPath;
   }

   public Masker getMasker() {
      return masker;
   }

   public void setMasker(Masker masker) {
      this.masker = masker;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      JsonMaskingUnit that = (JsonMaskingUnit) o;
      return Objects.equals(jsonPath, that.jsonPath) &&
              Objects.equals(masker, that.masker);
   }

   @Override
   public int hashCode() {

      return Objects.hash(jsonPath, masker);
   }
}
