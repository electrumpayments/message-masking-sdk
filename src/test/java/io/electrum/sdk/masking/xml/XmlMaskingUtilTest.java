package io.electrum.sdk.masking.xml;

import io.electrum.sdk.masking.MaskAll;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class XmlMaskingUtilTest {

   @Test
   public void testMasking() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response TransactionSeqNo=\"00000000\" ResponseCode=\"00\" ResponseMessage=\"Approved\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <MerchandiseItem PIN=\"2032001987992966\" ItemSerialNumber=\"SABT80HJGSMW\" Amount=\"1000\" NetworkName=\"MTN\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" DescriptiveValue=\"MTN Customer Care 173\"/>\n"
            + "        <MerchandiseIssuer Id=\"0213\"/>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";
      
      XmlMaskingUnit unit = new XmlMaskingUnit("//MerchandiseIssuer/@Id", new MaskAll());
      XmlMaskingUnit unit2 = new XmlMaskingUnit("//MerchandiseItem/@PIN", new MaskAll());
          
      String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"****************\"/>\n"
            + "        <MerchandiseIssuer Id=\"****\"/>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";
      
      Set<XmlMaskingUnit> units = new HashSet<>();
      units.add(unit);
      units.add(unit2);
      String result = XmlMaskingUtil.maskInXmlString(xml, units);
   
      assertEquals(expResult, result);
   }

   @Test
   public void testMaskTextNode() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response TransactionSeqNo=\"00000000\" ResponseCode=\"00\" ResponseMessage=\"Approved\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <MerchandiseItem PIN=\"2032001987992966\" ItemSerialNumber=\"SABT80HJGSMW\" Amount=\"1000\" NetworkName=\"MTN\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" DescriptiveValue=\"MTN Customer Care 173\"/>\n"
            + "        <MerchandiseIssuer Id=\"0213\"/>\n"
            + "        <Pin>2032001987992966</Pin>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";

      XmlMaskingUnit unit = new XmlMaskingUnit("/PrepaidMerchandise/Response/Pin/text()", new MaskAll());

      String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"2032001987992966\"/>\n"
            + "        <MerchandiseIssuer Id=\"0213\"/>\n"
            + "        <Pin>****************</Pin>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";

      Set<XmlMaskingUnit> units = new HashSet<>();
      units.add(unit);
      String result = XmlMaskingUtil.maskInXmlString(xml, units);

      assertEquals(expResult, result);
   }

   @Test
   public void testMaskDuplicateNodes() throws Throwable {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response TransactionSeqNo=\"00000000\" ResponseCode=\"00\" ResponseMessage=\"Approved\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <Tender Amount=\"500\"/>\n"
            + "        <Tender Amount=\"100\"/>\n"
            + "        <MerchandiseItem PIN=\"2032001987992966\" ItemSerialNumber=\"SABT80HJGSMW\" Amount=\"1000\" NetworkName=\"MTN\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" DescriptiveValue=\"MTN Customer Care 173\"/>\n"
            + "        <MerchandiseIssuer Id=\"0213\"/>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";

      XmlMaskingUnit unit = new XmlMaskingUnit("//Tender/@Amount", new MaskAll());

      String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"****\"/>\n"
            + "        <Tender Amount=\"***\"/>\n"
            + "        <Tender Amount=\"***\"/>\n"
            + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"2032001987992966\"/>\n"
            + "        <MerchandiseIssuer Id=\"0213\"/>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";

      Set<XmlMaskingUnit> units = new HashSet<>();
      units.add(unit);
      String result = XmlMaskingUtil.maskInXmlString(xml, units);

      assertEquals(expResult, result);
   }

   @Test
   public void testNoWhitespaceMasking() throws Exception {
      String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
              + "<PrepaidMerchandise>\n"
              + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
              + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
              + "        <Tender Amount=\"1000\"/>\n"
              + "        <Tender Amount=\"500\"/>\n"
              + "        <Tender Amount=\"100\"/>\n"
              + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"2032001987992966\"/>"
              + "        <MerchandiseIssuer Id=\"0213\"/>\n"
              + "    </Response>\n"
              + "    <Response>Response Text</Response>\n"
              + "</PrepaidMerchandise>";

      // Technically, the whitespace (spaces and newlines) in the first Response element count as text. We should be
      // able to mask the text contents of any node given a tag name without that whitespace being masked

      XmlMaskingUnit unit = new XmlMaskingUnit("//Response/text()", new MaskAll());

      String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
              + "<PrepaidMerchandise>\n"
              + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
              + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
              + "        <Tender Amount=\"1000\"/>\n"
              + "        <Tender Amount=\"500\"/>\n"
              + "        <Tender Amount=\"100\"/>\n"
              + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"2032001987992966\"/>"
              + "        <MerchandiseIssuer Id=\"0213\"/>\n"
              + "    </Response>\n"
              + "    <Response>*************</Response>\n"
              + "</PrepaidMerchandise>";

      Set<XmlMaskingUnit> units = new HashSet<>();
      units.add(unit);
      String result = XmlMaskingUtil.maskInXmlString(xml, units);

      assertEquals(expResult, result);
   }
}
