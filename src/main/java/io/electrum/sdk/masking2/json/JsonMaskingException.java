package io.electrum.sdk.masking2.json;

public class JsonMaskingException extends Exception {

   /**
    * Creates a new instance of <code>JsonMaskingException</code> without detail
    * message.
    */
   public JsonMaskingException() {
   }

   /**
    * Constructs an instance of <code>JsonMaskingException</code> with the
    * specified detail message.
    * @param message the detail message.
    */
   public JsonMaskingException(String message) {
      super(message);
   }

   public JsonMaskingException(String message, Throwable cause) {
      super(message, cause);
   }
}
