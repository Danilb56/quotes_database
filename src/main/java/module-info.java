module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.helpers;
    exports com.example.demo.models;
    opens com.example.demo.helpers to javafx.fxml;
    opens com.example.demo.models to javafx.fxml;
}