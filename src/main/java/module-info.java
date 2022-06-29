module com.example.restaurantmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;


    opens com.example.restaurantmanagement to javafx.fxml;
    exports com.example.restaurantmanagement;
}