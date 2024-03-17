package de.gkvsv.fhir.ta7.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

    public Document parseBundle(String fileName)
        throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        File bunleFile = new File(fileName);
        Document doc = dBuilder.parse(bunleFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private XPath createNewXPath() {
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

        return xpath;
    }

    public NodeList getNodeList(Object item, String expression) throws XPathExpressionException {
        XPath xPath =  createNewXPath();
        XPathExpression xPathExpression = xPath.compile(expression);
        NodeList nodeList = (NodeList) xPathExpression.evaluate(
            item, XPathConstants.NODESET);
        return nodeList;
    }

    private String getNodeValue(Object item, String expression) throws XPathExpressionException {
        XPath xPath =  createNewXPath();
        XPathExpression xPathExpression = xPath.compile(expression);
        String value = (String) xPathExpression.evaluate(item, XPathConstants.STRING);
        return value;
    }

    public Document createDocumentFromXMLFile(String ymlFileName)
        throws ParserConfigurationException, IOException, SAXException {
        // Load XML file
        File xmlFile = new File(ymlFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true); // Ensure that namespaces are handled
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        return doc;
    }

    public NodeList getInvoiceNodes(Document doc) throws XPathExpressionException {
        // Define the XPath expression to select Invoice nodes
        String invoiceExpression = "//fhir:Invoice";
        NodeList nodeList = getNodeList(doc, invoiceExpression);
        return nodeList;
    }

    public String getBelegnr(Node invoiceNode) throws XPathExpressionException {
        String belegnrPath = "./fhir:identifier[fhir:system/@value='https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Belegnummer']/fhir:value/@value";
        String belegnr = getNodeValue(invoiceNode, belegnrPath);
        return belegnr;
    }

    public String getERezeptID(Node invoiceNode) throws XPathExpressionException {
        String erezeptIdPath = "./fhir:identifier[fhir:system/@value='https://gematik.de/fhir/NamingSystem/PrescriptionID']/fhir:value/@value";
        String erezeptId = getNodeValue(invoiceNode, erezeptIdPath);
        return erezeptId;
    }

    public NodeList getZusatzdatenHerstellungNodes(Node invoiceNode)
        throws XPathExpressionException {
        String zdhPath = "./fhir:extension[@url='http://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_ZusatzdatenHerstellung']";
        NodeList nodeList = getNodeList(invoiceNode, zdhPath);
        return nodeList;
    }

    public String getAbschlagBetrag(Node zusatzdatenHerstellungNode, String abschalgCode)
        throws XPathExpressionException {
        String abschlagPthTemplate = ".//fhir:extension[@url='zuAbschlaegeZusatzdaten'][.//fhir:code/@value='****']";
        String abschlagPth = abschlagPthTemplate.replace("****",abschalgCode);
        NodeList abschlagNodes = getNodeList(zusatzdatenHerstellungNode, abschlagPth);

        if(abschlagNodes.getLength() > 0) {
            String betragPath = ".//fhir:value/@value";
            String betrag = getNodeValue(abschlagNodes.item(0),betragPath);

            return betrag;
        }

        return "";
    }

    public void extractCSVData(String bundleFile, OutputStreamWriter csvWriter)
        throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {

        XMLParser xmlParser = new XMLParser();

        Document document = xmlParser.parseBundle(bundleFile);
        NodeList allInvoiceNodes = xmlParser.getInvoiceNodes(document);

        String[] rabatte = {"R001", "R002", "R003", "R004", "R005"};

        for (int i = 0; i < allInvoiceNodes.getLength(); i++) {
            String[] rowData = new String[7];
            Node invoiceNode = allInvoiceNodes.item(i);
            String belegnr = xmlParser.getBelegnr(invoiceNode);
            rowData[0] = belegnr;

            String eRezeptID = xmlParser.getERezeptID(invoiceNode);
            rowData[1] = eRezeptID;

            NodeList herstellungNodes = xmlParser.getZusatzdatenHerstellungNodes(
                invoiceNode);
            Node firstHeestellungNode = herstellungNodes.item(0);

            for (int k = 0; k < rabatte.length; k++) {
                String rabatt = xmlParser.getAbschlagBetrag(firstHeestellungNode, rabatte[k]);
                rowData[k+2] = rabatt;
            }

            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
            csvWriter.flush();
        }

    }
}
