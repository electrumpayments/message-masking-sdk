package io.electrum.sdk.masking2.xml;

import io.electrum.sdk.masking2.Masker;

import java.util.Objects;

/**
 * Represents how a specific case of masking should be done within an XML message. This includes:
 *    - xpath, which is used to select relevant nodes from the XML message to be masked.
 *       (see https://msdn.microsoft.com/en-us/library/ms256086(v=vs.110).aspx)
 *    - masker, which is used to mask the selected nodes' values.
 */
public class XmlMaskingUnit {

   private String xpath;
   private Masker masker;

   public XmlMaskingUnit(String xpath, Masker masker) {
      this.xpath = xpath;
      this.masker = masker;
   }

   public Masker getMasker() {
      return masker;
   }

   public void setMasker(Masker masker) {
      this.masker = masker;
   }

   public String getXpath() {
      return xpath;
   }

   public void setXpath(String xpath) {
      this.xpath = xpath;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      XmlMaskingUnit that = (XmlMaskingUnit) o;
      return Objects.equals(xpath, that.xpath) && Objects.equals(masker, that.masker);
   }

   @Override
   public int hashCode() {
      return Objects.hash(xpath, masker);
   }
}
