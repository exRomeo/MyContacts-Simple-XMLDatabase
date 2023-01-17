module com.contacts.app.xmldatabase {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.xml;

    opens com.contacts.app.xmldatabase to javafx.fxml;
    exports com.contacts.app.xmldatabase;
}