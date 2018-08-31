package io.electrum.sdk.masking2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class DoNotPersistSerializer extends JsonSerializer<Object> {

   private String replacementValue;

   public DoNotPersistSerializer(String replacementValue) {
      this.replacementValue = replacementValue;
   }

   @Override
   public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeString(replacementValue);
   }
}