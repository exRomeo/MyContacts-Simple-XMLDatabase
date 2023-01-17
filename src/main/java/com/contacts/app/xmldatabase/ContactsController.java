package com.contacts.app.xmldatabase;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
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
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button deleteAllButton;
    private boolean isEditable;
    private boolean isClickable;
    private boolean isState;

    @FXML
    private void newContact() {
        if (isState) {
            isState = false;
            clearFields();
            newButton.setText("Save");
            editableSwitch();
            clickableSwitch();
            newButton.setDisable(false);
        } else {
            isState = true;
            newButton.setText("New");
            editableSwitch();
            clickableSwitch();
            if (!nameField.getText().isEmpty()) {
                addContact(getFields());
            }
            updateUI();
        }

    }

    @FXML
    private void updateContact() {
        if (isState) {
            isState = false;
            editButton.setText("Save");
            editableSwitch();
            clickableSwitch();
            editButton.setDisable(false);
        } else {
            isState = true;
            editButton.setText("Edit");
            editableSwitch();
            clickableSwitch();
            if (!nameField.getText().isEmpty()) {
                ContactsDAO.update(getFields());
            }
            updateUI();
        }

    }

    @FXML
    private void deleteContact() {
        ContactsDAO.delete();
        updateUI();
    }

    @FXML
    private void prevContact() {
        ContactsDAO.getPrev();
        updateUI();
    }

    @FXML
    private void searchContact() {
        if (isState) {
            clearFields();
            isState = false;
            searchButton.setText("Find");
            clickableSwitch();
            nameField.setEditable(true);
            searchButton.setDisable(false);
        } else {
            isState = true;
            searchButton.setText("Search");
            nameField.setEditable(false);
            clickableSwitch();
            ContactsDAO.search(nameField.getText());
            updateUI();
        }
    }

    @FXML
    private void nextContact() {
        ContactsDAO.getNext();
        updateUI();
    }


    @FXML
    private void deleteAllContacts() {
        deleteAll();
        updateUI();
        clearFields();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isEditable = false;
        isClickable = true;
        isState = true;
        editableSwitch();
        clickableSwitch();
        updateUI();
    }

    @FXML
    private void exitButton() {
        Platform.exit();
    }

    @FXML
    private void aboutButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "About", ButtonType.CLOSE);
        alert.setTitle("About");
        alert.setHeaderText("About App");
        alert.setContentText("Simple Database App");
        alert.showAndWait();
    }

    @FXML
    private void saveButton() {
        try {
            unloadContacts();
            loadContacts();
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            System.out.println("Error Saving Data !");
            ;
        }
    }

    private Contact getFields() {
        return new Contact(nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText());
    }

    private void updateUI() {
        if (totalContacts() > 0) {
            currentLabel.setText(getCurrent());
            totalLabel.setText(String.valueOf(totalContacts()));
            nameField.setText(getCurrentContact().getName());
            emailField.setText(getCurrentContact().getEmail());
            phoneField.setText(getCurrentContact().getPhone());
            addressField.setText(getCurrentContact().getAddress());
        } else {
            clearFields();
            currentLabel.setText("0");
            totalLabel.setText("0");
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
    }

    private void editableSwitch() {
        nameField.setEditable(isEditable);
        emailField.setEditable(isEditable);
        phoneField.setEditable(isEditable);
        addressField.setEditable(isEditable);
        isEditable = !isEditable;
    }

    private void clickableSwitch() {
        isClickable = !isClickable;
        newButton.setDisable(isClickable);
        editButton.setDisable(isClickable);
        deleteButton.setDisable(isClickable);
        prevButton.setDisable(isClickable);
        searchButton.setDisable(isClickable);
        nextButton.setDisable(isClickable);
        deleteAllButton.setDisable(isClickable);
    }

}