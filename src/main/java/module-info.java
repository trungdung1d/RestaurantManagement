module com.example.restaurantmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.restaurantmanagement to javafx.fxml;
    exports com.example.restaurantmanagement;
}