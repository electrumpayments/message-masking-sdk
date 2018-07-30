package io.electrum.sdk.masking2.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

public class XmlMaskingUtil {

   /**
    * Takes in a string containing some XML and a {@link Set} of {@link XmlMaskingUnit}s which dictate how the XML
    * should be masked.
    * 
    * @param xml
    *           the string containing the XML that needs to be masked.
    *           Null values are not valid XML and result in a JmlMaskingException
    * @param units
    *           which dictate which XML nodes should be masked (using an xpath) and how they should be masked.
    * @return A new string containing the masked version of the xml parameter.
    * @throws XmlMaskingException
    *            if any exception is encountered while parsing between a string and a {@link Document} or vice versa, or
    *            if any of the supplied xpaths are invalid.
    */
   public static String maskInXmlString(String xml, Set<XmlMaskingUnit> units) throws XmlMaskingException {
      if (xml == null) {
         throw new XmlMaskingException("Cannot mask null values");
      }

      final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder documentBuilder;
      try {
         documentBuilder = documentBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException ex) {
         throw new XmlMaskingException("Could not create DocumentBuilder", ex);
      }
      final XPathFactory xPathFactory = XPathFactory.newInstance();
      final XPath xPath = xPathFactory.newXPath();

      try {

         InputSource is = new InputSource();
         is.setCharacterStream(new StringReader(xml));

         Document document = documentBuilder.parse(is);

         for (XmlMaskingUnit unit : units) {
            NodeList nodes = (NodeList) xPath.compile(unit.getXpath()).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); ++i) {
               Node node = nodes.item(i);

               if (node != null) {
                  // There is no point in masking a string that is comprised of only whitespace
                  String nodeValue = node.getNodeValue();

                  if (nodeValue != null && !nodeValue.trim().isEmpty()) {
                     node.setNodeValue(unit.getMasker().mask(nodeValue));
                  }
               }
            }

         }

         return documentToXml(document, document.getXmlStandalone(), true);
      } catch (SAXException | IOException ex) {
         throw new XmlMaskingException("Parsing error while finding node for masking", ex);
      } catch (TransformerException ex) {
         throw new XmlMaskingException("Error transforming Document to XML String", ex);
      } catch (XPathExpressionException ex) {
         throw new XmlMaskingException("Error evaluating xpath", ex);
      }

   }

   private static String documentToXml(Document document, boolean standAlone, boolean indent)
         throws TransformerException {
      final TransformerFactory transformerFactory = TransformerFactory.newInstance();
      final Transformer transformer = transformerFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      transformer.setOutputProperty(OutputKeys.STANDALONE, standAlone ? "yes" : "no");
      transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");

      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      DOMSource source = new DOMSource(document);
      transformer.transform(source, result);
      String xml = sw.toString();

      return stripNewLineChar(xml).trim();

   }

   private static String stripNewLineChar(String s) {
      if (s.endsWith("\n")) {
         s = s.substring(0, s.length() - 1);
      }
      return s;
   }

}
