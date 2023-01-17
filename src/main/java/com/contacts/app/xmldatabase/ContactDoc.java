package com.contacts.app.xmldatabase;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactDoc {

    public static Document contactToDoc(Contact contact) throws ParserConfigurationException {
        Document doc = createDoc();
        Element root = createContactElement(doc, contact);
        doc.appendChild(root);
        return doc;
    }

    public static Contact docToContact(Document doc) {
        Element element = doc.getDocumentElement();
        return createContactObj(element);
    }

    public static Document contactListToDoc(List<Contact> contactList) throws ParserConfigurationException {
        Document doc = createDoc();
        Element root = doc.createElement("contacts");
        for (Contact contact : contactList) {
            root.appendChild(ContactDoc.createContactElement(doc, contact));
        }
        doc.appendChild(root);
        return doc;
    }

    public static List<Contact> docToContactList(Document contacts) {
        List<Contact> contactList = new ArrayList<>();

        NodeList list = contacts.getElementsByTagName("contact");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                contactList.add(createContactObj(element));
            }
        }
        return contactList;
    }

    private static Element createContactElement(Document doc, String name, String value) {
        Element root = doc.createElement(name);
        root.appendChild(doc.createTextNode(value));
        return root;
    }

    private static Element createContactElement(Document doc, Contact contact) {
        Element contactElement = doc.createElement("contact");
        contactElement.appendChild(createContactElement(doc, "name", contact.getName()));
        contactElement.appendChild(createContactElement(doc, "email", contact.getEmail()));
        contactElement.appendChild(createContactElement(doc, "phone", contact.getPhone()));
        contactElement.appendChild(createContactElement(doc, "address", contact.getAddress()));
        return contactElement;
    }

    public static Document contactsFileToDoc(File file) throws ParserConfigurationException, IOException, SAXException {
        return createBuilder().parse(file);
    }

    public static void docToContactsFile(Document doc, File dest) throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource src = new DOMSource(doc);
        StreamResult output = new StreamResult(new FileOutputStream(dest, false));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(src, output);
    }

    private static Contact createContactObj(Element element) {
        return new Contact(getTagValue(element, "name"),
                getTagValue(element, "email"),
                getTagValue(element, "phone"),
                getTagValue(element, "address"));
    }

    private static DocumentBuilder createBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
    }


    private static Document createDoc() throws ParserConfigurationException {
        DocumentBuilder builder = createBuilder();
        return builder.newDocument();
    }


    private static String getTagValue(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }


}
