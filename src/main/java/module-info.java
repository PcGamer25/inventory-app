module com.example.qkm2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.qkm2 to javafx.fxml;
    exports com.example.qkm2;
    exports com.example.qkm2.controller;
    opens com.example.qkm2.controller to javafx.fxml;
    exports com.example.qkm2.data;
    opens com.example.qkm2.data to javafx.fxml;
}