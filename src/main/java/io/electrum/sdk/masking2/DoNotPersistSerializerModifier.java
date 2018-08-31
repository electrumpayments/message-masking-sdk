package io.electrum.sdk.masking2;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;

import java.util.List;

public class DoNotPersistSerializerModifier extends BeanSerializerModifier {

   @Override
   public List<BeanPropertyWriter> changeProperties(
         SerializationConfig config,
         BeanDescription beanDesc,
         List<BeanPropertyWriter> beanProperties) {

      for (BeanPropertyWriter beanProperty : beanProperties) {
         DoNotPersist doNotPersist = beanProperty.getAnnotation(DoNotPersist.class);
         if (doNotPersist != null) {
            if (DoNotPersist.NULL_VALUE.equals(doNotPersist.replacementValue())) {
               beanProperty.assignSerializer(NullSerializer.instance);
            } else {
               beanProperty.assignSerializer(new DoNotPersistSerializer(doNotPersist.replacementValue()));
            }
         }
      }

      return beanProperties;
   }
}