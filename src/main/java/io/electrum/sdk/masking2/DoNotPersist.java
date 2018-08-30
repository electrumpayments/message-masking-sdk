package io.electrum.sdk.masking2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation to decorate a data element that should not be saved to persistent storage.
 * <p>
 * For example, PAN, PIN, PIN block, etc. may not be persisted.
 * </p>
 * <p>
 * By default the value will be serialized as if not set. If the message specification requires a value to be present,
 * specify a non-empty string {@link #replacementValue()} value to use in place of the real value to ensure
 * deserialization does not fail.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DoNotPersist {
   public static final String NULL_VALUE = "DEFAULT_NULL_VALUE";

   /**
    * By default this parameter may be omitted, which will result in the annotated field to be encoded as null. Set to
    * the string representation of the value to be used instead if the messaging specification requires a value to be
    * present.
    *
    * @return the value to be written to persistent storage instead of the real value
    */
   String replacementValue() default NULL_VALUE;
}
