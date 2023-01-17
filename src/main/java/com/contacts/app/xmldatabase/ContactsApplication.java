package com.contacts.app.xmldatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ContactsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            ContactsDAO.loadContacts();
        } catch (ParserConfigurationException e) {
            System.out.println("Couldn't load Contacts");
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ContactsApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("My Contacts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("C:\\Users\\ramy3\\IdeaProjects\\XMLDatabase\\src\\main\\resources\\icons\\icon.png"));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ContactsDAO.unloadContacts();
    }

    public static void main(String[] args) {
        launch();
    }
}