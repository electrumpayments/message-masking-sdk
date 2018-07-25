package io.electrum.sdk.masking2.xml;

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
      
      io.electrum.sdk.masking2.xml.XmlMaskingUnit unit = new io.electrum.sdk.masking2.xml.XmlMaskingUnit("//MerchandiseIssuer/@Id", new io.electrum.sdk.masking2.MaskAll());
      io.electrum.sdk.masking2.xml.XmlMaskingUnit unit2 = new io.electrum.sdk.masking2.xml.XmlMaskingUnit("//MerchandiseItem/@PIN", new io.electrum.sdk.masking2.MaskAll());
          
      String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<PrepaidMerchandise>\n"
            + "    <Response ResponseCode=\"00\" ResponseMessage=\"Approved\" TransactionSeqNo=\"00000000\">\n"
            + "        <Product NetworkID=\"2\" ProductID=\"Airtime\" Type=\"TELEPHONE PREPAY\" UserID=\"0828385603\"/>\n"
            + "        <Tender Amount=\"1000\"/>\n"
            + "        <MerchandiseItem Amount=\"1000\" DescriptiveValue=\"MTN Customer Care 173\" ItemMessage=\"How to recharge - Dial *141*Pin Number#|MTN Customer Care 173\" ItemSerialNumber=\"SABT80HJGSMW\" NetworkName=\"MTN\" PIN=\"****************\"/>\n"
            + "        <MerchandiseIssuer Id=\"****\"/>\n"
            + "    </Response>\n"
            + "</PrepaidMerchandise>";
      
      Set<io.electrum.sdk.masking2.xml.XmlMaskingUnit> units = new HashSet<>();
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

      io.electrum.sdk.masking2.xml.XmlMaskingUnit unit = new io.electrum.sdk.masking2.xml.XmlMaskingUnit("/PrepaidMerchandise/Response/Pin/text()", new io.electrum.sdk.masking2.MaskAll());

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

      Set<io.electrum.sdk.masking2.xml.XmlMaskingUnit> units = new HashSet<>();
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

      io.electrum.sdk.masking2.xml.XmlMaskingUnit unit = new io.electrum.sdk.masking2.xml.XmlMaskingUnit("//Tender/@Amount", new io.electrum.sdk.masking2.MaskAll());

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

      Set<io.electrum.sdk.masking2.xml.XmlMaskingUnit> units = new HashSet<>();
      units.add(unit);
      String result = XmlMaskingUtil.maskInXmlString(xml, units);

      assertEquals(expResult, result);
   }
}
