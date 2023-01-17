package com.contacts.app.xmldatabase;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.contacts.app.xmldatabase.ContactDoc.*;

public class ContactsDAO {
    private static List<Contact> contactsList;
    private static int current = 0;

    public static List<Contact> getContactsList() {
        if (contactsList == null) {
            contactsList = new ArrayList<>();
        }
        return contactsList;
    }

    private static boolean isDuplicate(Contact contact) {
        return contactsList.contains(contact);
    }

    public static String getCurrent() {
        return String.valueOf(current + 1);
    }

    public static void addContact(Contact contact) {
        contactsList.add(contact);
        current = 0;
    }

    public static void update(Contact updatedContact) {
        contactsList.set(current, updatedContact);
    }

    public static void delete(Contact contact) {
        contactsList.remove(contact);
        if(current >0) {
            current--;
        }
    }

    public static String totalContacts() {
        return String.valueOf(contactsList.size());
    }

    public static void deleteAll() {
        contactsList.clear();
        current = 0;
    }

    public static Contact getFirst() {
        return contactsList.get(0);
    }

    public static void getNext() {
        if (current + 1 < contactsList.size()) {
            current++;
        }
    }

    public static Contact getCurrentContact(){
        return contactsList.get(current);
    }

    public static Contact getPrev() {
        if (current > 0) {
            current--;
        }
        return contactsList.get(current);
    }

    public static void loadContacts() throws IOException, ParserConfigurationException {
        File file = createFile();
        try {
            contactsList = docToContactList(contactsFileToDoc(file));
        } catch (SAXException e) {
            System.out.println("File is Empty");
        }
    }

    public static void unloadContacts() throws IOException, ParserConfigurationException, TransformerException {
        File file = createFile();
        docToContactsFile(contactListToDoc(contactsList), file);
    }

    private static File createFile() throws IOException {
        File file = new File("myContactsList.xml");
        if (file.createNewFile()) {
            System.out.println("A New File Has Been Created Successfully");
        }
        return file;
    }
}
