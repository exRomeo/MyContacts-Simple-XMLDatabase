package com.contacts.app.xmldatabase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.contacts.app.xmldatabase.ContactsDAO.*;

public class ContactsController implements Initializable {
    @FXML
    private Label currentLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private void nextContact(){
        ContactsDAO.getNext();
        updateUI();
    }

    @FXML
    private void prevContact(){
        ContactsDAO.getPrev();
        updateUI();
    }

    @FXML
    private void deleteContact(){
        ContactsDAO.delete(getFields());
        updateUI();
    }

    @FXML
    private void updateContact(){
        ContactsDAO.update(getFields());
        updateUI();
    }

    @FXML
    private void newContact(){
        addContact(getFields());
        updateUI();
    }
    private void updateUI(){
        currentLabel.setText(getCurrent());
        totalLabel.setText(totalContacts());
        nameField.setText(getCurrentContact().getName());
        emailField.setText(getCurrentContact().getEmail());
        phoneField.setText(getCurrentContact().getPhone());
        addressField.setText(getCurrentContact().getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTheFields();
    }
    private Contact getFields(){
        return new Contact(nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText());
    }

    private void fillTheFields(){
        currentLabel.setText("0");
        if(!getContactsList().isEmpty()){
            totalLabel.setText(totalContacts());
            nameField.setText(getFirst().getName());
            emailField.setText(getFirst().getEmail());
            phoneField.setText(getFirst().getPhone());
            addressField.setText(getFirst().getAddress());
        } else {
            totalLabel.setText("0");
        }
    }
}