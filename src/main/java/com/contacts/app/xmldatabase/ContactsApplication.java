package com.contacts.app.xmldatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class ContactsApplication extends Application {
    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ContactsApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.getIcons().add(new Image(String.valueOf((Objects.requireNonNull(ContactsApplication.class.getResource("icon.png")).toURI()))));
            stage.setTitle("My Contacts");
            stage.setResizable(false);
            ContactsDAO.loadContacts();
            stage.show();
        } catch (IOException | ParserConfigurationException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        ContactsDAO.unloadContacts();
    }

    public static void main(String[] args) {
        launch();
    }
}