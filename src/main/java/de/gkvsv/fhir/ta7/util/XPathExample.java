package de.gkvsv.fhir.ta7.util;

import java.io.File;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathExample {

    public static void main(String[] args) {
        try {
            // Load XML file
            File xmlFile = new File("src/test/resources/ref/rezeptBundle.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true); // Ensure that namespaces are handled
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Define the XPath expression with default namespace and attribute condition
            //String expression = "//fhir:identifier[@system='https://gematik.de/fhir/NamingSystem/PrescriptionID']/fhir:value";
            //String expression = "//ns:identifier[@system='https://gematik.de/fhir/NamingSystem/PrescriptionID']/ns:value";
            // Define the XPath expression
            //String expression = "//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/*[local-name()='value']";
            String expression = "//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/*[local-name()='value']";

            // Create XPath
            XPath xpath = XPathFactory.newInstance().newXPath();


            // Evaluate XPath expression against document
            NodeList nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

            // Iterate through the NodeList and print the values
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("Identifier Value: " + element.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
