package io.electrum.sdk.masking2.maskablevalue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.electrum.sdk.masking2.MaskAll;
import io.electrum.sdk.masking2.Masker;

import java.io.IOException;

public class MaskableValuePersistenceSerializer extends StdSerializer<MaskableValue> {

   private static final long serialVersionUID = 1L;
   private final static Masker masker = new MaskAll();

   public MaskableValuePersistenceSerializer() {
      super(MaskableValue.class);
   }

   @Override
   public void serialize(MaskableValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      switch (value.getStorage()) {
      case NOT_STORED:
         gen.writeNull();
         break;
      case STORED_CLEAR:
         gen.writeString(value.getValue());
         break;
      case STORED_ENCRYPTED:
         // we do not support encrypting from this serializer yet, so just be safe and don't write it
         gen.writeNull();
      }
   }
}
