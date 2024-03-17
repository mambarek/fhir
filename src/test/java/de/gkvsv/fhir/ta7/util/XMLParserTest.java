package de.gkvsv.fhir.ta7.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class XMLParserTest {

    @Test
    void parseBundle()
        throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        XMLParser xmlParser = new XMLParser();
        String filePath = "src/test/resources/ref/rezeptBundle.xml";

        Document document = xmlParser.parseBundle(filePath);
        NodeList allInvoiceNodes = xmlParser.getInvoiceNodes(document);
        assertEquals(1,allInvoiceNodes.getLength());

        String[] rabatte = {"R001", "R002", "R003", "R004", "R005"};

        for (int i = 0; i < allInvoiceNodes.getLength(); i++) {
            Node invoiceNode = allInvoiceNodes.item(i);
            String belegnr = xmlParser.getBelegnr(invoiceNode);
            System.out.println("Belegnr: " + belegnr);

            String eRezeptID = xmlParser.getERezeptID(invoiceNode);
            System.out.println("eRezeptID: " + eRezeptID);

            NodeList herstellungNodes = xmlParser.getZusatzdatenHerstellungNodes(
                invoiceNode);
            assertEquals(1,herstellungNodes.getLength());
            Node firstHeestellungNode = herstellungNodes.item(0);

            for (int k = 0; k < rabatte.length; k++) {
                String rabatt = xmlParser.getAbschlagBetrag(firstHeestellungNode, rabatte[k]);
                System.out.println("Rabatt " + rabatte[k] + ": " + rabatt);
            }
        }
    }

    @Test
    void test_extractCSVData()
        throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        XMLParser parser = new XMLParser();
        String filePath = "src/test/resources/ref/rezeptBundle.xml";
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        parser.extractCSVData(filePath, writer);
    }

    void testParser() throws ParserConfigurationException, IOException, SAXException {
        XMLParser xmlParser = new XMLParser();
        String filePath = "src/test/resources/ref/rezeptBundle.xml";

        Document doc = xmlParser.createDocumentFromXMLFile(filePath);
    }

    @Test
    void printAllERezepte()
        throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        XMLParser xmlParser = new XMLParser();
        String filePath = "src/test/resources/ref/rezeptBundle.xml";
        //String filePath = "/Users/mmbarek/Development/projects/fhir/src/test/resources/ref/rezeptBundle.xml";

        Document document = xmlParser.parseBundle(filePath);
        //String expression = "/Bundle/entry[4]/resource/Invoice/identifier[1]";
        //String expression = "//Invoice/identifier[system[@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/value";

        // Define the XPath expression with default namespace
       // String expression = "//*[local-name()='Bundle' and //Invoice/identifier[system[@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/*";
// Define the XPath expression with default namespace and attribute condition
        //String expression = "//fhir:identifier[@system='https://gematik.de/fhir/NamingSystem/PrescriptionID']/fhir:value";
// Define the XPath expression with local-name() to ignore namespaces
       // String expression = "//*[local-name()='identifier' and @system='https://gematik.de/fhir/NamingSystem/PrescriptionID']/*[local-name()='value']";

        // Define the XPath expression
        String expression = "//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/*[local-name()='value']";
        // Create XPath
        NodeList nodeList = xmlParser.getNodeList(document, expression);
        System.out.println("nodeList count: " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String value = element.getAttribute("value");
                System.out.println("ERezept-ID: " + value);
            }
            //System.out.println("\nCurrent Element :" + nNode.getNodeName() + " value: " + nNode.getAttributes().g);
        }

        //Invoice/identifier[system[@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/value

        String invoiceEL = "//*[local-name()='invoice']";
        NodeList invoiceNodes = xmlParser.getNodeList(document, expression);
        System.out.println("Invoice count: " + invoiceNodes.getLength());
        // Iterate through the Invoice nodes
        for (int i = 0; i < invoiceNodes.getLength(); i++) {
            Node invoiceNode = invoiceNodes.item(i);
            XPath xPath =  XPathFactory.newInstance().newXPath();
            // Select children with name 'identifier' and 'system' with the specified value
            String suExpression = ".//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]";
            //String suExpression = ".//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]/*[local-name()='value']";
            NodeList identifierNodes = (NodeList) xPath.evaluate(suExpression, invoiceNode, XPathConstants.NODESET);
            System.out.println("identifierNodes count: " + identifierNodes.getLength());
            // Iterate through the identifierNodes and process them as needed
            for (int j = 0; j < identifierNodes.getLength(); j++) {
                Node identifierNode = identifierNodes.item(j);
                if (identifierNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element identifierElement = (Element) identifierNode;
                    String value = identifierElement.getTextContent(); // Or read the value attribute if needed
                    System.out.println("Identifier Value: " + value);
                }
            }
        }
    }

    @Test
    void testExample1() {

            try {
                // Load XML file
                File xmlFile = new File("src/test/resources/ref/rezeptBundle.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setNamespaceAware(true); // Ensure that namespaces are handled
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);

                // Define the XPath expression to select Invoice nodes
                String invoiceExpression = "//fhir:Invoice";

                // Create XPath
                XPath xpath = XPathFactory.newInstance().newXPath();
                xpath.setNamespaceContext(new NamespaceContext() {
                    @Override
                    public String getNamespaceURI(String prefix) {
                        if ("fhir".equals(prefix)) {
                            return "http://hl7.org/fhir";
                        }
                        return null;
                    }

                    @Override
                    public String getPrefix(String namespaceURI) {
                        return null;
                    }

                    @Override
                    public Iterator<String> getPrefixes(String namespaceURI) {
                        return null;
                    }
                });

                // Evaluate XPath expression to select Invoice nodes
                NodeList invoiceNodes = (NodeList) xpath.evaluate(invoiceExpression, doc, XPathConstants.NODESET);

                // Iterate through the Invoice nodes
                for (int i = 0; i < invoiceNodes.getLength(); i++) {
                    Node invoiceNode = invoiceNodes.item(i);

                    // Select children with name 'identifier' and 'system' with the specified value
                    //NodeList identifierNodes = (NodeList) xpath.evaluate(".//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]", invoiceNode, XPathConstants.NODESET);
                    NodeList identifierNodes = (NodeList) xpath.evaluate(".//*[local-name()='identifier'][*[local-name()='system'][@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']]", invoiceNode, XPathConstants.NODESET);

                    // Iterate through the identifierNodes and process them as needed
                    for (int j = 0; j < identifierNodes.getLength(); j++) {
                        Node identifierNode = identifierNodes.item(j);
                        if (identifierNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element identifierElement = (Element) identifierNode;
                            String value = identifierElement.getTextContent(); // Or read the value attribute if needed
                            System.out.println("Identifier Value: " + value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Test
        void testExample2() {
            try {
                // Load XML file
                File xmlFile = new File("src/test/resources/ref/rezeptBundle.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setNamespaceAware(true); // Ensure that namespaces are handled
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);

                // Define the XPath expression to select Invoice nodes
                String invoiceExpression = "//fhir:Invoice";

                // Create XPath
                XPath xpath = XPathFactory.newInstance().newXPath();
                xpath.setNamespaceContext(new NamespaceContext() {
                    @Override
                    public String getNamespaceURI(String prefix) {
                        if ("fhir".equals(prefix)) {
                            return "http://hl7.org/fhir";
                        }
                        return null;
                    }

                    @Override
                    public String getPrefix(String namespaceURI) {
                        return null;
                    }

                    @Override
                    public Iterator<String> getPrefixes(String namespaceURI) {
                        return null;
                    }
                });

                // Evaluate XPath expression to select Invoice nodes
                NodeList invoiceNodes = (NodeList) xpath.evaluate(invoiceExpression, doc, XPathConstants.NODESET);
                System.out.println("Invoice count: " + invoiceNodes.getLength());
                // Iterate through the Invoice nodes
                for (int i = 0; i < invoiceNodes.getLength(); i++) {
                    Node invoiceNode = invoiceNodes.item(i);

                    String belegnrPath = "./fhir:identifier[fhir:system/@value='https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Belegnummer']/fhir:value/@value";
                    String belegnr = (String) xpath.evaluate(belegnrPath, invoiceNode, XPathConstants.STRING);
                    // Output the value attribute
                    System.out.println("Belegnr: " + belegnr);

                    // Select the value attribute of the child node named 'value' with the specified value
                    String erezeptIdPath = "./fhir:identifier[fhir:system/@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']/fhir:value/@value";
                    String erezeptId = (String) xpath.evaluate(erezeptIdPath, invoiceNode, XPathConstants.STRING);
                    // Output the value attribute
                    System.out.println("ERezeptID: " + erezeptId);

                    //zaehlerHerstellung
                    String zdhPath = "./fhir:extension[@url='http://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_ZusatzdatenHerstellung']";
                    NodeList zdhNodes = (NodeList) xpath.evaluate(zdhPath, invoiceNode, XPathConstants.NODESET);
                    System.out.println("zaehlerHerstellung count: " + zdhNodes.getLength());

                    // zeige alle zuAbschlaegeZusatzdaten
                    for (int j = 0; j < zdhNodes.getLength(); j++) {
                        Node zdhNode = zdhNodes.item(j);
                        //String abschlagPthR001 = "./fhir:zuAbschlaegeZusatzdaten[.//fhir:code[value='R001']]";
                        //String zuAbschlaegeZusatzdaten = ".//fhir:extension[@url='zuAbschlaegeZusatzdaten']";
                        //NodeList zuAbschlaegeZusatzdatenNodes = (NodeList) xpath.evaluate(zuAbschlaegeZusatzdaten, zdhNode, XPathConstants.NODESET);
                        //System.out.println("zuAbschlaegeZusatzdatenNodes count: " + zuAbschlaegeZusatzdatenNodes.getLength());
                    }

                    // zeige alle abschlagPthR001
                    for (int j = 0; j < zdhNodes.getLength(); j++) {
                        Node zdhNode = zdhNodes.item(j);
                        // Define the XPath expression to select 'extension' nodes with URL 'zuAbschlaegeZusatzdaten'
                        //String abschlagPthR001 = ".//fhir:extension[fhir:url='zuAbschlaegeZusatzdaten']/fhir:extension[fhir:code/fhir:coding/fhir:code/@value='R001']";
                        //String abschlagPthR001 = "./fhir:zuAbschlaegeZusatzdaten[.//fhir:code[value='R001']]";
                        String abschlagPthR001 = ".//fhir:extension[@url='zuAbschlaegeZusatzdaten'][.//fhir:code/@value='R001']";
                        NodeList abschlagNodesR001 = (NodeList) xpath.evaluate(abschlagPthR001, zdhNode, XPathConstants.NODESET);
                        System.out.println("abschlagNodes count: " + abschlagNodesR001.getLength());

                        if(abschlagNodesR001.getLength() > 0) {
                            String betragPath = ".//fhir:value/@value";
                            String betrag = (String)xpath.evaluate(betragPath, abschlagNodesR001.item(0),
                                XPathConstants.STRING);

                            System.out.println("Betrag R001: " + betrag);
                        }

                        String abschlagPthR004 = ".//fhir:extension[@url='zuAbschlaegeZusatzdaten'][.//fhir:code/@value='R004']";
                        NodeList abschlagNodesR004 = (NodeList) xpath.evaluate(abschlagPthR004, zdhNode, XPathConstants.NODESET);
                        System.out.println("abschlagNodes count: " + abschlagNodesR004.getLength());

                        if(abschlagNodesR004.getLength() > 0) {
                            String betragPath = ".//fhir:value/@value";
                            String betrag = (String)xpath.evaluate(betragPath, abschlagNodesR004.item(0),
                                XPathConstants.STRING);

                            System.out.println("Betrag R004: " + betrag);
                        }

                    }



                    // /Bundle/entry[4]/resource/Invoice/extension[2]/extension[2]/extension[2]/extension[2]/extension[1]/valueCodeableConcept/coding/code/@value
                    // /extension[2]/extension[2]/extension[2]/extension[1]/valueCodeableConcept/coding/code/@value
                    // Select children with name 'identifier' within each Invoice node
                    NodeList identifierNodes = (NodeList) xpath.evaluate("./fhir:identifier", invoiceNode, XPathConstants.NODESET);
                    System.out.println("identifierNodes count:" + identifierNodes.getLength());
                    // Iterate through the identifierNodes and process them as needed
                    for (int j = 0; j < identifierNodes.getLength(); j++) {
                        Node identifierNode = identifierNodes.item(j);
                        if (identifierNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element identifierElement = (Element) identifierNode;
                            String value = identifierElement.getTextContent(); // Or read the value attribute if needed
                            System.out.println("Identifier Value: " + value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



}