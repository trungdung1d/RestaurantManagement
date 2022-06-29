package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagerResgisterController {

    Connection connection = DBConnection.getConnections();
    @FXML
    private Button SignUp;

    @FXML
    private JFXTextField idField;

    @FXML
    private Button login;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXTextField username;

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene(event, "Login.fxml", "Login");
    }

    @FXML
    void handleSignUpAction(ActionEvent event) throws SQLException {
        String ID = idField.getText();
        String Username = username.getText();
        String Pass = pass.getText();
        if (Username.isEmpty() || Pass.isEmpty()) {
            Main.showAlert(Alert.AlertType.WARNING, "Error", "Field can not be empty!");
        } else {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO manager (USERNAME, ID, PASSWORD) VALUES(?,?,?)");
            statement.setString(1, Username);
            statement.setString(2, ID);
            statement.setString(3,Pass);
            try {
                statement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Successful", "Sign up Successful!");

            } catch (SQLException e) {
                Main.showAlert(Alert.AlertType.ERROR, "Error", "Account already exists with this ID!");
            } finally {
                DBConnection.closeConnections();
            }
        }
    }
}
