module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires javafx.media;
    requires org.controlsfx.controls;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}