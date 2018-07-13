package io.electrum.sdk.masking;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation to mark fields in a bean property class that should be masked when traced.
 * <p>
 * For example:
 * 
 * <pre>
 * public class MyMessage {
 *    &#64;Masked(MaskPan.class)
 *    String cardNumber;
 *    &#64;ShortDescriptionField
 *    String messageId;
 *    String someOtherField;
 *    ...
 * </pre>
 * 
 * The above snippet will ensure that pan is masked (eg: 123456******1234) in trace messages
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Masked {
   Class<? extends Masker> value() default MaskAll.class;
}
