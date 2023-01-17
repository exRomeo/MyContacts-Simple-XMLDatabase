package com.contacts.app.xmldatabase;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.contacts.app.xmldatabase.ContactDoc.*;

public class ContactsDAO {
    private static List<Contact> contactsList;
    private static int current = 0;

    public static String getCurrent() {
        if (contactsList.isEmpty()) {
            return "0";
        } else {
            return String.valueOf(current + 1);
        }
    }

    public static void addContact(Contact contact) {
        contactsList.add(contact);
        current = contactsList.size() - 1;
    }

    public static void update(Contact updatedContact) {
        if (contactsList.size() > 0)
            contactsList.set(current, updatedContact);
    }

    public static void delete() {
        if (!contactsList.isEmpty()) {
            contactsList.remove(current);
            if (current > 0)
                current--;
        }
    }

    public static int totalContacts() {
        return contactsList.size();
    }

    public static void deleteAll() {
        contactsList.clear();
        current = 0;
    }

    public static void getNext() {
        if (current + 1 < contactsList.size())
            current++;
    }

    public static Contact getCurrentContact() {
        return contactsList.get(current);
    }

    public static void getPrev() {
        if (current > 0)
            current--;
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

    public static void search(String name) {
        if (contactsList.size() > 0) {
            current = 0;
            while (current + 1 < contactsList.size() && !contactsList.get(current).getName().equals(name)) {
                System.out.println(current);
                current++;
            }
        }
    }

    private static File createFile() throws IOException {
        File file = new File("myContactsList.xml");
        if (file.createNewFile()) {
            System.out.println("A New File Has Been Created Successfully");
        }
        return file;
    }
}
