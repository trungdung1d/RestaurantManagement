package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.*;


public class ManagerForgotPass {

    Connection connection = DBConnection.getConnections();
    @FXML
    private JFXTextField iDField;

    @FXML
    private Button login;

    @FXML
    private JFXPasswordField passField;

    @FXML
    private Button save;

    @FXML
    private JFXTextField userField;

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene(event, "Login.fxml", "Login");
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
        String username = userField.getText();
        String password = passField.getText();
        String ID = iDField.getText();
        if (username.equals("") || password.equals("") || ID.equals("")) {
            Main.showAlert(Alert.AlertType.WARNING, "Error", "Field can not be empty!");
        } else {
            String sql = "SELECT * FROM manager WHERE USERNAME=? AND ID=?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, ID);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String update = "UPDATE manager set PASSWORD=? WHERE ID=?";
                    PreparedStatement statement1 = connection.prepareStatement(update);
                    statement1.setString(2, ID);
                    statement1.setString(1, password);
                    Main.showAlert(Alert.AlertType.INFORMATION, "Successful", "Password Set Successfully!");
                    statement1.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBConnection.closeConnections();
            }
        }
    }

}
