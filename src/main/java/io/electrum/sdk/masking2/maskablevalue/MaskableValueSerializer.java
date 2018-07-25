package io.electrum.sdk.masking2.maskablevalue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.electrum.sdk.masking2.MaskAll;
import io.electrum.sdk.masking2.Masker;

import java.io.IOException;

public class MaskableValueSerializer extends StdSerializer<MaskableValue> {

   private static final long serialVersionUID = 1L;

   public MaskableValueSerializer() {
      super(MaskableValue.class);
   }

   private final static Masker masker = new MaskAll();

   @Override
   public void serialize(MaskableValue value, JsonGenerator gen, SerializerProvider serializers)
         throws IOException, JsonProcessingException {

      if (value.isSensitive()) {
         gen.writeString(masker.mask(value.getValue()));
      } else {
         gen.writeString(value.getValue());
      }
   }
}
