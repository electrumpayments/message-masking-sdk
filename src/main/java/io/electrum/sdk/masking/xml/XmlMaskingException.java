package io.electrum.sdk.masking.xml;

public class XmlMaskingException extends Exception {

   /**
    * Creates a new instance of <code>XmlMaskingException</code> without detail
    * message.
    */
   public XmlMaskingException() {
   }

   /**
    * Constructs an instance of <code>XmlMaskingException</code> with the
    * specified detail message.
    * @param message the detail message.
    */
   public XmlMaskingException(String message) {
      super(message);
   }

   public XmlMaskingException(String message, Throwable cause) {
      super(message, cause);
   }
}
