package io.electrum.sdk.masking2.maskablevalue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.electrum.sdk.masking2.MaskAll;
import io.electrum.sdk.masking2.Masker;

import java.io.IOException;

public class MaskableValueSerializer extends StdSerializer<MaskableValue> {

   private static final long serialVersionUID = 1L;
   private static final Masker masker = new MaskAll();

   public MaskableValueSerializer() {
      super(MaskableValue.class);
   }

   @Override
   public void serialize(MaskableValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

      if (value.isSensitive()) {
         gen.writeString(masker.mask(value.getValue()));
      } else {
         gen.writeString(value.getValue());
      }
   }
}
